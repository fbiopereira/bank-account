package com.fbiopereira.bankaccount.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fbiopereira.bankaccount.domain.enums.OperationType;

public class BankOperationsRequest {

    @JsonProperty(required = true)
    private OperationType type;
    private String origin;

    @JsonProperty(required = true)
    private int amount;

    private String destination;


    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
