package com.banco.fraudvalidation.service.approvalstrategy;

import com.banco.fraudvalidation.service.valitationfraudstrategy.ValidationStrategy;

import java.util.Map;

public interface ApprovalStrategy {
    boolean approve(Map<ValidationStrategy, Boolean> validationResults);
}