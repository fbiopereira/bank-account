package com.fbiopereira.bankaccount.dto;

import com.fbiopereira.bankaccount.domain.model.Account;

public class BankOperationsResponse {

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }

    Account destination;

}
