package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.TransactionDetailDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.CartRepository;
import com.codeblue.montreISTA.repository.TransactionRepository;
import com.codeblue.montreISTA.repository.OrderRepository;
import com.codeblue.montreISTA.repository.TransactionDetailsRepository;
import com.codeblue.montreISTA.service.CategoryService;
import com.codeblue.montreISTA.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionDetailsRepository transactionDetailsRepository;
    private CartRepository cartRepository;
    private OrderRepository orderRepository;
    private CategoryService categoryService;

    @Override
    public List<TransactionResponseDTO> findAllTransaction() {
        return transactionRepository.findAllByOrderByHistoryTransactionIdAsc().stream()
                .map(HistoryTransaction::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDetailDTO> findAllTransactionDetail() {
        return transactionDetailsRepository.findAllByOrderByTransactionDetailIdAsc().stream()
                .map(HistoryTransactionDetail::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> findByTransactionBuyerId(Long id) throws Exception {
        return transactionRepository.findByBuyerBuyerIdOrderByHistoryTransactionIdAsc(id).stream()
                .map(HistoryTransaction::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> findByTransactionSellerId(Long id) throws Exception {
        return transactionRepository.findBySellerSellerIdOrderByHistoryTransactionIdAsc(id).stream()
                .map(HistoryTransaction::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDetailDTO> findByTransactionDetailBuyerId(Long id) throws Exception {
        return transactionDetailsRepository.findByHistoryTransactionBuyerBuyerIdOrderByTransactionDetailIdAsc(id).stream()
                .map(HistoryTransactionDetail::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDetailDTO> findByTransactionDetailSellerId(Long id) throws Exception {
        return transactionDetailsRepository.findByHistoryTransactionSellerSellerIdOrderByTransactionDetailIdAsc(id).stream()
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
    public TransactionDetailDTO findByTransactionDetailId(Long id) throws Exception {
        Optional<HistoryTransactionDetail> transactionDetailOptional = transactionDetailsRepository.findById(id);
        if(transactionDetailOptional.isEmpty()){
            throw new Exception("Transaction not found");
        }
        return transactionDetailOptional.get().convertToResponse();
    }

    @Override
    public String createTransaction(Long id) throws Exception {
        Optional<Order> orderOptional = orderRepository.findFirstByListCartBuyerBuyerIdOrderByCreatedAtDesc(id);
        if(orderOptional.isEmpty()){
            throw new Exception("Please order first");
        }
        Order order = orderOptional.get();
        for(Cart cart:order.getListCart()){
            HistoryTransaction transaction = new HistoryTransaction();
            HistoryTransactionDetail transactionDetail = new HistoryTransactionDetail();

            List<Photo> photo = cart.getProduct().getPhotos();
            String photoURL;
            String photoName;
            boolean checkURL = photo.stream().map(Photo::getPhotoURL).findAny().isEmpty();
            boolean checkName = photo.stream().map(Photo::getPhotoName).findAny().isEmpty();
            if(checkURL || checkName){
                photoURL = "-";
                photoName = "-";
            }else {
                photoURL = photo.get(0).getPhotoURL();
                photoName = photo.get(0).getPhotoName();
            }
            List<Category> categories = categoryService.findByProductId(cart.getProduct().getProductId());
            String category = categories.stream()
                    .map(Category::getName)
                    .collect(Collectors.joining(","));
            transaction.setBuyer(cart.getBuyer());
            transaction.setSeller(cart.getProduct().getSeller());
            transaction.setPhotoName(photoName);
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
            transactionDetailsRepository.save(transactionDetail);
        }
        Order orderDelete = orderRepository.findFirstByListCartBuyerBuyerIdOrderByCreatedAtDesc(id).orElseThrow(Exception::new);
        orderRepository.deleteById(orderDelete.getOrderId());
        List<Cart> Carts = cartRepository.findByBuyerBuyerIdOrderByModifiedAtDesc(id);
        cartRepository.deleteAll(Carts);
        return "Order Success, transactions saved";
    }

}
