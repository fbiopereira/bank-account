package com.fbiopereira.bankaccount.dto;

import com.fbiopereira.bankaccount.domain.model.Account;

public class BankOperationsDepositWithdrawResponse {

    Account destination;

    public BankOperationsDepositWithdrawResponse(Account account){

        this.destination = account;

    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }

}
