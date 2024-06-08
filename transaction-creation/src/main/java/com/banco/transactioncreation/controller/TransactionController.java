package com.banco.transactioncreation.controller;

import com.banco.transactioncreation.model.Transaction;
import com.banco.transactioncreation.service.TransactionService;
import com.banco.transactioncreation.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private final ValidationService<Transaction> validationService;

    public TransactionController(TransactionService transactionService, ValidationService<Transaction> validationService) {
        this.transactionService = transactionService;
        this.validationService = validationService;
    }

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Transaction> createTransaction(@RequestBody Mono<Transaction> transactionMono) {
        return transactionMono.flatMap(transaction -> {
            validationService.validate(transaction);
            Transaction preparedTransaction = transactionService.prepareTransaction(transaction);
            CompletableFuture.runAsync(() -> transactionService.saveAndPublishTransaction(preparedTransaction));
            return Mono.just(preparedTransaction);
        });
    }
}