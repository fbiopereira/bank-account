package com.fbiopereira.bankaccount.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fbiopereira.bankaccount.domain.model.Account;

public class BankOperationsResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Account origin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Account destination;

    public BankOperationsResponse(Account originAccount, Account destinationAccount){
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
