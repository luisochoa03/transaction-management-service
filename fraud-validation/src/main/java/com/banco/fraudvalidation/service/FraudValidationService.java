package com.banco.fraudvalidation.service;

import com.banco.fraudvalidation.model.Transaction;
import com.banco.fraudvalidation.model.TransactionStatus;
import com.banco.fraudvalidation.producer.TransactionProducer;
import com.banco.fraudvalidation.service.approvalstrategy.ApprovalStrategy;
import com.banco.fraudvalidation.service.approvalstrategy.OptionalValidationApprovalStrategy;
import com.banco.fraudvalidation.service.approvalstrategy.RequiredValidationApprovalStrategy;
import com.banco.fraudvalidation.service.valitationfraudstrategy.ValidationStrategy;
import com.banco.fraudvalidation.service.valitationfraudstrategy.ValueValidationStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FraudValidationService {

    private final List<ValidationStrategy> validationStrategies;
    private final List<ApprovalStrategy> approvalStrategies;
    private final TransactionProducer transactionProducer;

    public FraudValidationService(TransactionProducer transactionProducer) {
        this.validationStrategies = new ArrayList<>();
        this.validationStrategies.add(new ValueValidationStrategy());
        this.approvalStrategies = new ArrayList<>();
        this.approvalStrategies.add(new RequiredValidationApprovalStrategy());
        this.approvalStrategies.add(new OptionalValidationApprovalStrategy(validationStrategies));
        this.transactionProducer = transactionProducer;
    }

    public void validateAndPublish(Transaction transaction) {
        Map<ValidationStrategy, Boolean> validationResults = validateTransaction(transaction);
        boolean isValid = approvalStrategies.stream()
                .allMatch(strategy -> strategy.approve(validationResults));
        updateTransactionStatus(transaction, isValid);

        transactionProducer.publish(transaction);
    }

    private Map<ValidationStrategy, Boolean> validateTransaction(Transaction transaction) {
        Map<ValidationStrategy, Boolean> validationResults = new HashMap<>();

        for (ValidationStrategy strategy : validationStrategies) {
            boolean result = strategy.validate(transaction);
            validationResults.put(strategy, result);
        }

        return validationResults;
    }


    private void updateTransactionStatus(Transaction transaction, boolean isValid) {
        TransactionStatus status = isValid ? TransactionStatus.APPROVED : TransactionStatus.REJECTED;
        transaction.setTransactionStatus(status);
    }

}