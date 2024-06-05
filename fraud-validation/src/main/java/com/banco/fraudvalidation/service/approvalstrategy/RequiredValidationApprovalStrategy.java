package com.banco.fraudvalidation.service.approvalstrategy;

import com.banco.fraudvalidation.service.valitationfraudstrategy.ValidationStrategy;

import java.util.Map;

public class RequiredValidationApprovalStrategy implements ApprovalStrategy {

    @Override
    public boolean approve( Map<ValidationStrategy, Boolean> validationResults) {
        return validationResults.entrySet().stream().noneMatch(entry -> entry.getKey().isRequired() && !entry.getValue());
    }
}