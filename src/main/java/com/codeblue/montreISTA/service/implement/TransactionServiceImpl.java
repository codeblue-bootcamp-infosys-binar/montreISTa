package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.TransactionDetailResponseDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.helper.Pagination;
import com.codeblue.montreISTA.repository.*;
import com.codeblue.montreISTA.service.CategoryService;
import com.codeblue.montreISTA.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailsRepository transactionDetailsRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CategoryService categoryService;
    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;

    @Override
    public List<TransactionResponseDTO> findAllTransaction(Integer page, String sort, boolean descending) {

        Pageable pageable = Pagination.paginate(page, sort, descending);

        return transactionRepository.findAllByOrderByHistoryTransactionIdAsc(pageable).stream()
                .map(HistoryTransaction::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDetailResponseDTO> findAllTransactionDetail(Integer page, String sort, boolean descending) {

        Pageable pageable = Pagination.paginate(page, sort, descending);

        return transactionDetailsRepository.findAllByOrderByTransactionDetailIdAsc(pageable).stream()
                .map(HistoryTransactionDetail::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> findByTransactionBuyerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception {
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Please order first"));

        Pageable pageable = Pagination.paginate(page, sort, descending);

        List<HistoryTransaction> transaction = transactionRepository.findByBuyerBuyerIdOrderByHistoryTransactionIdAsc(buyer.getBuyerId(), pageable);
        if(transaction.isEmpty()){
            throw new Exception("You don't have order");
        }
        return transaction.stream()
                .map(HistoryTransaction::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> findByTransactionSellerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception {
        Seller seller = sellerRepository.findByUserIdUsername(authentication.getName()).orElseThrow(()->new Exception("You don't have store"));

        Pageable pageable = Pagination.paginate(page, sort, descending);

        List<HistoryTransaction> transaction = transactionRepository.findBySellerSellerIdOrderByHistoryTransactionIdAsc(seller.getSellerId(), pageable);
        if(transaction.isEmpty()){
            throw new Exception("You don't have product");
        }
        return transaction.stream()
                .map(HistoryTransaction::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDetailResponseDTO> findByTransactionDetailBuyerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception {
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Please order first"));

        Pageable pageable = Pagination.paginate(page, sort, descending);

        List<HistoryTransactionDetail> transactionDetail = transactionDetailsRepository.findByHistoryTransactionBuyerBuyerIdOrderByTransactionDetailIdAsc(buyer.getBuyerId(), pageable);
        if(transactionDetail.isEmpty()){
            throw new Exception("You don't have order");
        }
        return transactionDetailsRepository.findByHistoryTransactionBuyerBuyerIdOrderByTransactionDetailIdAsc(buyer.getBuyerId(), pageable).stream()
                .map(HistoryTransactionDetail::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDetailResponseDTO> findByTransactionDetailSellerId(Authentication authentication, Integer page, String sort, boolean descending) throws Exception {
        Seller seller = sellerRepository.findByUserIdUsername(authentication.getName()).orElseThrow(()->new Exception("You don't have store"));

        Pageable pageable = Pagination.paginate(page, sort, descending);

        List<HistoryTransactionDetail> transactionDetail = transactionDetailsRepository.findByHistoryTransactionSellerSellerIdOrderByTransactionDetailIdAsc(seller.getSellerId(),pageable);

        if(transactionDetail.isEmpty()){
            throw new Exception("You don't have product");
        }
        return transactionDetail.stream()
                .map(HistoryTransactionDetail::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionResponseDTO findByTransactionId(Long id) throws Exception {
        Optional<HistoryTransaction> transactionOptional = transactionRepository.findById(id);
        if(transactionOptional.isEmpty()){
            throw new Exception("Transaction not found");
        }
        return transactionOptional.get().convertToResponse();
    }

    @Override
    public TransactionDetailResponseDTO findByTransactionDetailId(Long id) throws Exception {
        Optional<HistoryTransactionDetail> transactionDetailOptional = transactionDetailsRepository.findById(id);
        if(transactionDetailOptional.isEmpty()){
            throw new Exception("Transaction not found");
        }
        return transactionDetailOptional.get().convertToResponse();
    }

    @Override
    public List<TransactionDetailResponseDTO> createTransaction(Authentication authentication) throws Exception {
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Please order first"));
        Optional<Order> orderOptional = orderRepository.findFirstByListCartBuyerBuyerIdOrderByOrderIdDesc(buyer.getBuyerId());
        if(orderOptional.isEmpty()){
            throw new Exception("Please order first");
        }
        Order order = orderOptional.get();
        List<TransactionDetailResponseDTO> results = new ArrayList<>();
        for(Cart cart:order.getListCart()){
            HistoryTransaction transaction = new HistoryTransaction();
            HistoryTransactionDetail transactionDetail = new HistoryTransactionDetail();

            List<Photo> photo = cart.getProduct().getPhotos();
            String photoURL;
            boolean checkURL = photo.stream().map(Photo::getPhotoURL).findAny().isEmpty();
            if(checkURL){
                photoURL = "-";
            }else {
                photoURL = photo.get(0).getPhotoURL();
            }
            List<Category> categories = categoryService.findByProductId(cart.getProduct().getProductId());
            String category = categories.stream()
                    .map(Category::getName)
                    .collect(Collectors.joining(","));
            transaction.setBuyer(cart.getBuyer());
            transaction.setSeller(cart.getProduct().getSeller());
            transaction.setPhotoUrl(photoURL);
            transaction.setProduct_id(cart.getProduct().getProductId());
            transaction.setProduct_name(cart.getProduct().getProductName());
            transaction.setProduct_price(cart.getProduct().getPrice());
            transaction.setQuantity(cart.getQuantity());
            transaction.setTotalPrice(cart.getQuantity()*cart.getProduct().getPrice()+order.getShipping().getPrice());
            HistoryTransaction transactionSave = transactionRepository.save(transaction);
            transactionDetail.setHistoryTransaction(transactionSave);
            transactionDetail.setDestinationName(order.getDestinationName());
            transactionDetail.setDestinationAddress(order.getDestinationAddress());
            transactionDetail.setDestinationPhone(order.getDestinationPhone());
            transactionDetail.setZipCode(order.getZipCode());
            transactionDetail.setPaymentName(order.getPayment().getName());
            transactionDetail.setPaymentCode(order.getPayment().getPaymentCode());
            transactionDetail.setShippingName(order.getShipping().getName());
            transactionDetail.setShippingPrice(order.getShipping().getPrice());
            transactionDetail.setCategories(category);
            transactionDetail.setProductDescription(cart.getProduct().getDescription());
            HistoryTransactionDetail transactionDetailSave = transactionDetailsRepository.save(transactionDetail);
            results.add(transactionDetailSave.convertToResponse());
        }
        Order orderDelete = orderRepository.findFirstByListCartBuyerBuyerIdOrderByOrderIdDesc(buyer.getBuyerId()).orElseThrow(()->new Exception("Order not found"));
        orderRepository.deleteById(orderDelete.getOrderId());
        return results;
    }

}
