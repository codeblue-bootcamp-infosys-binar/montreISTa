package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private CartRepository cartRepository;
    private CategoryService categoryService;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private BuyerRepository buyerRepository;
    private PaymentRepository paymentRepository;
    private ShippingRepository shippingRepository;

    @Override
    public List<TransactionResponseDTO> findAll() {
        List<Transaction> results = transactionRepository.findAllByOrderByTransactionIdAsc();
        return this.convertListDTO(results);
    }

    @Override
    public List<TransactionResponseDTO> findByBuyer(String keyword) throws Exception {
        List<Transaction> results = transactionRepository.findByOrderListCartBuyerUserName(keyword);
        if(results==null){
            throw new Exception("Transaction not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<TransactionResponseDTO> findBySeller(String keyword) throws Exception {
        List<Transaction> results = transactionRepository.findByOrderListCartProductSellerUserIdName(keyword);
        if(results==null){
            throw new Exception("Transaction not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<TransactionResponseDTO> findByProductName(String keyword) throws Exception {
        List<Transaction> results = transactionRepository.findByOrderListCartProductProductName(keyword);
        if(results==null){
            throw new Exception("Transaction not found");
        }
        return this.convertListDTO(results);
    }


    @Override
    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        Optional<Order> orderId = orderRepository.findById(transactionRequestDTO.getOrder_id());
        Order order = orderId.get();
        Transaction transaction = transactionRequestDTO.convertToEntity(order);
        transactionRepository.save(transaction);
        return this.convertDTO(transaction);
    }

    @Override
    public TransactionResponseDTO updateTransaction(TransactionRequestDTO transactionRequestDTO,Long id) throws Exception {
        Optional<Transaction> transactionId = transactionRepository.findById(id);
        if(transactionId.isEmpty()){
            throw new Exception("Transaction not found");
        }
        Optional<Order> orderId = orderRepository.findById(transactionRequestDTO.getOrder_id());
        Order order = orderId.get();
        Transaction transaction = transactionRequestDTO.convertToEntity(order);
        transaction.setTransactionId(id);
        transactionRepository.save(transaction);
        return this.convertDTO(transaction);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Optional<Transaction> transactionId = transactionRepository.findById(id);
        if(transactionId.isEmpty()){
            throw new Exception("Transaction not found");
        }
        transactionRepository.deleteById(id);
    }

    public List<TransactionResponseDTO> convertListDTO(List<Transaction> transactions){
        List<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();
        for(Transaction transaction:transactions){
            TransactionResponseDTO transactionDTO = convertDTO(transaction);
            transactionResponseDTOS.add(transactionDTO);
        }
        return transactionResponseDTOS;
    }

    public TransactionResponseDTO convertDTO(Transaction transaction){
        List<CartResponseDTO> cartDTOS = new ArrayList<>();

        for(Cart cart:transaction.getOrder().getListCart()){
            List<PhotoProductDTO> photosDTO = cart.getProduct().getPhotos().stream()
                    .map(Photo::convertToProduct)
                    .collect(Collectors.toList());
            List<Category> categories = categoryService.findByProductId(cart.getProduct().getProductId());
            List<String> categoriesDTO = new ArrayList<>();
            for (Category category : categories) {
                String categoryDTO = category.getName();
                categoriesDTO.add(categoryDTO);
            }
            CartResponseDTO cartDTO = cart.convertToResponse(photosDTO,categoriesDTO);
            cartDTOS.add(cartDTO);
        }
        return transaction.convertToResponse(cartDTOS);
    }
}
