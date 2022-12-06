package com.fbiopereira.bankaccount.domain.model;

import com.fbiopereira.bankaccount.domain.enums.AccountErrorMessages;
import com.fbiopereira.bankaccount.domain.enums.OperationType;
import com.fbiopereira.bankaccount.domain.exceptions.InvalidOperationException;

import static com.fbiopereira.bankaccount.domain.enums.AccountErrorMessages.INVALID_ACCOUNT_ID;
import static com.fbiopereira.bankaccount.domain.enums.AccountErrorMessages.TRANSFER_DENIED_IN_DOMAIN;

public class Account {

private int id;
private int balance;

public Account(int id){

    if (id > 0) {
        this.id = id;
        this.balance = 0;
    }
    else{
        throw new InvalidOperationException(INVALID_ACCOUNT_ID.getCode(), INVALID_ACCOUNT_ID.getMessage());
    }
}


    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void doOperation(int amount, OperationType operation) {
        switch (operation) {
            case deposit -> this.balance = this.balance + amount;
            case withdraw -> this.balance = this.balance - amount;
            case transfer ->
                    throw new InvalidOperationException(TRANSFER_DENIED_IN_DOMAIN.getCode(), TRANSFER_DENIED_IN_DOMAIN.getMessage());
        }
    }
}
