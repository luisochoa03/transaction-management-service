package com.banco.fraudvalidation.service.valitationfraudstrategy;

import com.banco.fraudvalidation.model.Transaction;

public interface ValidationStrategy {
    boolean validate(Transaction transaction);
    boolean isRequired();
}