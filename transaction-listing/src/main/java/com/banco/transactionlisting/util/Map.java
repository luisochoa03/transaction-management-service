package com.banco.transactionlisting.util;

import com.banco.transactionlisting.model.TransactionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Map {
    private Map() {
    }

    public static  TransactionResponse convertSearchResponseToTransactionResponse(String sourceAsString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(sourceAsString, TransactionResponse.class);

    }

}
