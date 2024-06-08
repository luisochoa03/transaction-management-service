package com.banco.transactioncreation.utils;

import com.banco.commonmodels.dto.TransactionKafkaDTO;
import com.banco.commonmodels.dto.TransactionStatus;
import com.banco.transactioncreation.exception.TransactionNullException;
import com.banco.transactioncreation.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private TransactionMapper() {
    }

    public static TransactionKafkaDTO toTransactionKafkaDTO(Transaction transaction) {
        if (transaction == null) {
            throw new TransactionNullException("transaction no puede ser null");
        }

        TransactionKafkaDTO transactionKafkaDTO = new TransactionKafkaDTO();
        transactionKafkaDTO.setAccountExternalIdDebit(transaction.getAccountExternalIdDebit());
        transactionKafkaDTO.setAccountExternalIdCredit(transaction.getAccountExternalIdCredit());
        transactionKafkaDTO.setTransferTypeId(transaction.getTransferTypeId());
        transactionKafkaDTO.setValue(transaction.getValue());
        transactionKafkaDTO.setTransactionExternalId(transaction.getTransactionExternalId());
        transactionKafkaDTO.setCreatedAt(transaction.getCreatedAt());
        transactionKafkaDTO.setTransactionStatus(TransactionStatus.PENDING);


        return transactionKafkaDTO;
    }
}
