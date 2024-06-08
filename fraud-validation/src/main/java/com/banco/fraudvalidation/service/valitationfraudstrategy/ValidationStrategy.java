package com.banco.fraudvalidation.service.valitationfraudstrategy;

import com.banco.commonmodels.dto.TransactionKafkaDTO;

public interface ValidationStrategy {
    boolean validate(TransactionKafkaDTO transaction);
    boolean isRequired();
}