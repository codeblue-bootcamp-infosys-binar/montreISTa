package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.TransactionDetailResponseDTO;
import com.codeblue.montreISTA.DTO.TransactionResponseDTO;
import com.codeblue.montreISTA.controller.TransactionController;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.helper.Pagination;
import com.codeblue.montreISTA.repository.*;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.CategoryService;
import com.codeblue.montreISTA.service.TransactionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    public static Integer currentPage;

    private final TransactionRepository transactionRepository;
    private final TransactionDetailsRepository transactionDetailsRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;
    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private static final String Line = "====================";

    @Override
    public ResponseEntity<Object> findAllTransaction(Integer page, String sort, boolean descending) {
        try {
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<TransactionResponseDTO> transactions = transactionRepository.findAll(pageable).stream()
                    .map(HistoryTransaction::convertToResponse)
                    .collect(Collectors.toList());
            logger.info("==================== Logger Start Get All Transactions     ====================");
            for (TransactionResponseDTO transactionData : transactions) {
                logger.info("-------------------------");
                logger.info("Transactions ID  : " + transactionData.getTransaction_id());
                logger.info("Store Name       : " + transactionData.getStore_name());
                logger.info("Product Name     : " + transactionData.getProduct_name());
                logger.info("Product Price    : " + transactionData.getProduct_price());
                logger.info("Photo url        : " + transactionData.getPhoto_url());
                logger.info("Buyer ID         : " + transactionData.getBuyer_id());
                logger.info("Seller ID        : " + transactionData.getSeller_id());
                logger.info("Quantity         : " + transactionData.getQuantity());
                logger.info("Total Price      : " + transactionData.getTotal_price());
            }
            logger.info("==================== Logger End Get AlL Transactions   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, transactions);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findAllTransactionDetail(Integer page, String sort, boolean descending) {
        try {
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<TransactionDetailResponseDTO> results = transactionDetailsRepository.findAll(pageable).stream()
                    .map(HistoryTransactionDetail::convertToResponse)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Get Transaction Detail" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Transaction Detail" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions details", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions detail doesn't exist!");
        }
    }

    @Override
    public ResponseEntity<Object> findByTransactionBuyerId(Authentication authentication, Integer page, String sort, boolean descending) {
        try {
            Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("Please order first"));
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<HistoryTransaction> transaction = transactionRepository.findByBuyerBuyerId(buyer.getBuyerId(), pageable);
            if (transaction.isEmpty()) {
                throw new Exception("You don't have order");
            }
            List<TransactionResponseDTO> results = transaction.stream()
                    .map(HistoryTransaction::convertToResponse)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Get Transactions Buyer" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Transactions Buyer" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions doesn't exist!");
        }
    }

    @Override
    public ResponseEntity<Object> findByTransactionSellerId(Authentication authentication, Integer page, String sort, boolean descending) {
        try {
            Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("You don't have store"));
            Pageable pageable = Pagination.paginate(page, sort, descending);
            List<HistoryTransaction> transaction = transactionRepository.findBySellerSellerId(seller.getSellerId(), pageable);
            if (transaction.isEmpty()) {
                throw new Exception("You don't have product");
            }
            List<TransactionResponseDTO> results = transaction.stream()
                    .map(HistoryTransaction::convertToResponse)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Get Transaction By Seller" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Transaction By Seller" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions doesn't exist!");
        }
    }

    @Override
    public ResponseEntity<Object> findByTransactionDetailBuyerId(Authentication authentication, Integer page, String sort, boolean descending) {
        try {
            Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("Please order first"));
            Pageable pageable = Pagination.paginate(page, sort, descending);

            List<HistoryTransactionDetail> transactionDetail = transactionDetailsRepository.findByHistoryTransactionBuyerBuyerId(buyer.getBuyerId(), pageable);
            if (transactionDetail.isEmpty()) {
                throw new Exception("You don't have order");
            }

            List<TransactionDetailResponseDTO> results = transactionDetailsRepository.findByHistoryTransactionBuyerBuyerId(buyer.getBuyerId(), pageable).stream()
                    .map(HistoryTransactionDetail::convertToResponse)
                    .collect(Collectors.toList());

            logger.info(Line + "Logger Start Get Transactions Detail Buyer" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Transactions Detail Buyer" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions details", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions detail doesn't exist!");
        }
    }

    @Override
    public ResponseEntity<Object> findByTransactionDetailSellerId(Authentication authentication, Integer page, String sort, boolean descending) {
        try {
            Seller seller = sellerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("You don't have store"));
            Pageable pageable = Pagination.paginate(page, sort, descending);

            List<HistoryTransactionDetail> transactionDetail = transactionDetailsRepository.findByHistoryTransactionSellerSellerId(seller.getSellerId(), pageable);
            if (transactionDetail.isEmpty()) {
                throw new Exception("You don't have product");
            }

            List<TransactionDetailResponseDTO> results = transactionDetail.stream()
                    .map(HistoryTransactionDetail::convertToResponse)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Get Transactions Detail Seller" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Transactions Detail Seller" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions details", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions detail doesn't exist!");
        }
    }

    @Override
    public ResponseEntity<Object> findByTransactionId(Long id) {
        try {
            HistoryTransaction transaction = transactionRepository.findById(id).orElseThrow(() -> new Exception("Transaction not found"));
            TransactionResponseDTO results = transaction.convertToResponse();
            logger.info(Line + "Logger Start Get History Transactions " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get History Transactions " + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "History transactions doesn't exist!");
        }
    }

    @Override
    public ResponseEntity<Object> findByTransactionDetailId(Long id) {
        try {
            HistoryTransactionDetail transactionDetail = transactionDetailsRepository.findById(id).orElseThrow(() -> new Exception("Transaction not found"));
            TransactionDetailResponseDTO results = transactionDetail.convertToResponse();
            logger.info(Line + "Logger Start Get By ID" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By ID" + Line);
            return ResponseHandler.generateResponse("successfully retrieved history transactions details", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Transactions detail doesn't exist!");
        }
    }

    @Override
    public ResponseEntity<Object> createTransaction(Authentication authentication) {
        try {
            Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("Please order first"));
            Order order = orderRepository.findFirstByListCartBuyerBuyerIdOrderByOrderIdDesc(buyer.getBuyerId()).orElseThrow(() -> new Exception("Please order first"));

            if (!order.getIsPay()) {
                throw new Exception("Please pay first !");
            }

            List<TransactionDetailResponseDTO> results = new ArrayList<>();
            for (Cart cart : order.getListCart()) {
                HistoryTransaction transaction = new HistoryTransaction();
                HistoryTransactionDetail transactionDetail = new HistoryTransactionDetail();

                List<Photo> photo = cart.getProduct().getPhotos();
                String photoURL;
                boolean checkURL = photo.stream().map(Photo::getPhotoURL).findAny().isEmpty();
                if (checkURL) {
                    photoURL = "-";
                } else {
                    photoURL = photo.get(0).getPhotoURL();
                }

                List<Category> categories = categoryRepository.findByProductsProductId(cart.getProduct().getId());
                String category = categories.stream()
                        .map(Category::getName)
                        .collect(Collectors.joining(","));

                transaction.setBuyer(cart.getBuyer());
                transaction.setSeller(cart.getProduct().getSeller());
                transaction.setPhotoUrl(photoURL);
                transaction.setProduct_id(cart.getProduct().getId());
                transaction.setProduct_name(cart.getProduct().getProductName());
                transaction.setProduct_price(cart.getProduct().getPrice());
                transaction.setQuantity(cart.getQuantity());
                transaction.setTotalPrice(cart.getQuantity() * cart.getProduct().getPrice() + order.getShipping().getPrice());
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
            Order orderDelete = orderRepository.findFirstByListCartBuyerBuyerIdOrderByOrderIdDesc(buyer.getBuyerId()).orElseThrow(() -> new Exception("Order not found"));
            orderRepository.deleteById(orderDelete.getOrderId());

            logger.info(Line + "Logger Start Get Checkout Order" + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Checkout Order " + Line);
            return ResponseHandler.generateResponse("Successfully Transaction", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Doing Transactions");
        }
    }



}
