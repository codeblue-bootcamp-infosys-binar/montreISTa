package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.CartRepository;
import com.codeblue.montreISTA.repository.HistoryTransactionRepository;
import com.codeblue.montreISTA.repository.OrderRepository;
import com.codeblue.montreISTA.repository.TransactionDetailsRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private HistoryTransactionRepository transactionRepository;
    private TransactionDetailsRepository transactionDetailsRepository;
    private CartRepository cartRepository;
    private OrderRepository orderRepository;
    private CategoryService categoryService;

    @Override
    public List<HistoryTransaction> findAllTransaction() {
        return transactionRepository.findAllByOrderByHistoryTransactionIdAsc();
    }

    @Override
    public List<HistoryTransactionDetail> findAllTransactionDetail() {
        return transactionDetailsRepository.findAllByOrderByTransactionDetailIdAsc();
    }

    @Override
    public List<HistoryTransaction> findByTransactionBuyerId(Long id) throws Exception {
        return transactionRepository.findByBuyerBuyerIdOrderByHistoryTransactionIdAsc(id);
    }

    @Override
    public List<HistoryTransaction> findByTransactionSellerId(Long id) throws Exception {
        return transactionRepository.findBySellerSellerIdOrderByHistoryTransactionIdAsc(id);
    }

    @Override
    public List<HistoryTransactionDetail> findByTransactionDetailBuyerId(Long id) throws Exception {
        return transactionDetailsRepository.findByHistoryTransactionBuyerBuyerIdOrderByTransactionDetailIdAsc(id);
    }

    @Override
    public List<HistoryTransactionDetail> findByTransactionDetailSellerId(Long id) throws Exception {
        return transactionDetailsRepository.findByHistoryTransactionSellerSellerIdOrderByTransactionDetailIdAsc(id);
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
            Photo photoget = photo.get(0);
            String photoName = photoget.getPhotoName();
            String photoUrl = photoget.getPhotoURL();
            List<Category> categories = categoryService.findByProductId(cart.getProduct().getProductId());
            String category = categories.stream()
                    .map(Category::getName)
                    .collect(Collectors.joining(","));
            transaction.setBuyer(cart.getBuyer());
            transaction.setSeller(cart.getProduct().getSeller());
            transaction.setPhotoName(photoName);
            transaction.setPhotoUrl(photoUrl);
            transaction.setTotalPrice(order.getTotalprice());
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
            transactionDetail.setProductId(cart.getProduct().getProductId());
            transactionDetail.setProductName(cart.getProduct().getProductName());
            transactionDetail.setProductPrice(cart.getProduct().getPrice());
            transactionDetail.setProductDescription(cart.getProduct().getDescription());
            transactionDetail.setQuantity(cart.getQuantity());
            HistoryTransactionDetail transactionDetailSave = transactionDetailsRepository.save(transactionDetail);
        }
        Optional<Order> orderDelete = orderRepository.findFirstByListCartBuyerBuyerIdOrderByCreatedAtDesc(id);
        orderRepository.deleteById(orderDelete.get().getOrderId());
        List<Cart> Carts = cartRepository.findByBuyerBuyerIdOrderByCreatedAtDesc(id);
        return "Order Success, transactions saved";
    }

}
