package com.banco.fraudvalidation.service.approvalstrategy;

import com.banco.fraudvalidation.service.valitationfraudstrategy.ValidationStrategy;

import java.util.List;
import java.util.Map;

public class OptionalValidationApprovalStrategy implements ApprovalStrategy {

    private final List<ValidationStrategy> validationStrategies;

    public OptionalValidationApprovalStrategy(List<ValidationStrategy> validationStrategies) {
        this.validationStrategies = validationStrategies;
    }

    @Override
    public boolean approve( Map<ValidationStrategy, Boolean> validationResults) {
        long totalOptionalValidations = validationStrategies.stream().filter(s -> !s.isRequired()).count();
        long successfulOptionalValidations = validationResults.entrySet().stream()
                .filter(entry -> !entry.getKey().isRequired() && entry.getValue())
                .count();

        return successfulOptionalValidations >= totalOptionalValidations * 0.9;
    }
}