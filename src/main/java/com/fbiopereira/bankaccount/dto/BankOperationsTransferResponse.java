package com.fbiopereira.bankaccount.dto;

import com.fbiopereira.bankaccount.domain.model.Account;

public class BankOperationsTransferResponse {


    Account origin;
    Account destination;

    public BankOperationsTransferResponse(Account originAccount, Account destinationAccount){
        this.destination = destinationAccount;
        this.origin = originAccount;
    }


    public Account getOrigin() {
        return origin;
    }

    public void setorigin(Account origin) {
        this.origin = origin;
    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }



}
