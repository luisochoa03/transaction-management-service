package com.banco.transactionlisting.controller;

import com.banco.transactionlisting.model.Transaction;
import com.banco.transactionlisting.model.TransactionResponse;
import com.banco.transactionlisting.service.TransactionListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class TransactionListingController {

    private final TransactionListingService transactionListingService;

    @Autowired
    public TransactionListingController(TransactionListingService transactionListingService) {
        this.transactionListingService = transactionListingService;
    }

    @PostMapping("/filter-transactions")
    public ResponseEntity<List<TransactionResponse>> filterTransactions(@Validated @RequestBody Transaction transaction) throws IOException {
            List<TransactionResponse> transactions = transactionListingService.getTransactions(transaction);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}