package com.fbiopereira.bankaccount.dto;

import com.fbiopereira.bankaccount.domain.enums.OperationType;

public class BankOperationsRequest {

    private OperationType type;

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private int destination;
    private int amount;


}
