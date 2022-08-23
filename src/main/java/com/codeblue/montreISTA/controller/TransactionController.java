package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "09. Transaction")
@SecurityRequirement(name = "bearer-key")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/dashboard/transactions")
    public ResponseEntity<Object> findAllHistoryTransaction(@RequestParam(required = false) String sort,
                                                            @RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) boolean descending) {
        return transactionService.findAllTransaction(page, sort, descending);
    }

    @GetMapping("/dashboard/transactions/{id}")
    public ResponseEntity<Object> findByHistoryTransactionId(@PathVariable Long id) throws Exception {
        return transactionService.findByTransactionId(id);
    }

    @GetMapping("/user/my-transactions/buyer")
    public ResponseEntity<Object> findTransactionByBuyer(Authentication authentication,
                                                         @RequestParam(required = false) String sort,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) boolean descending) throws Exception {

        return transactionService.findByTransactionBuyerId(authentication, page, sort, descending);
    }

    @GetMapping("/user/my-transactions/seller")
    public ResponseEntity<Object> findTransactionBySeller(Authentication authentication,
                                                          @RequestParam(required = false) String sort,
                                                          @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) boolean descending) throws Exception {

        return transactionService.findByTransactionSellerId(authentication, page, sort, descending);
    }


    @GetMapping("/dashboard/transaction-details")
    public ResponseEntity<Object> findAllTransactionDetails(@RequestParam(required = false) String sort,
                                                            @RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) boolean descending) {
        return transactionService.findAllTransactionDetail(page, sort, descending);
    }

    @GetMapping("/dashboard/transactions-detail/{id}")
    public ResponseEntity<Object> findTransactionDetailsId(@PathVariable Long id) throws Exception {

        return transactionService.findByTransactionDetailId(id);

    }

    @GetMapping("/user/my-transactions-detail/buyer")
    public ResponseEntity<Object> findTransactionDetailsByBuyer(Authentication authentication,
                                                                @RequestParam(required = false) String sort,
                                                                @RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) boolean descending) throws Exception {
        return transactionService.findByTransactionDetailBuyerId(authentication, page, sort, descending);
    }

    @GetMapping("/user/my-transactions-detail/seller")
    public ResponseEntity<Object> findTransactionDetailBySeller(Authentication authentication,
                                                                @RequestParam(required = false) String sort,
                                                                @RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) boolean descending) throws Exception {

        return transactionService.findByTransactionDetailSellerId(authentication, page, sort, descending);
    }

    @GetMapping("/user/checkout-order")
    public ResponseEntity<Object> postCart(Authentication authentication) throws Exception {

        return transactionService.createTransaction(authentication);

    }
}
