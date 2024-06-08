package com.banco.transactionlisting.model;


import javax.validation.constraints.NotNull;

public class TransactionType {
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

