package com.fbiopereira.bankaccount.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fbiopereira.bankaccount.domain.enums.OperationType;

public class BankOperationsRequest {

    @JsonProperty(required = true)
    private OperationType type;
    private int origin;

    @JsonProperty(required = true)
    private int amount;

    private int destination;


    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
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


}
