package com.fbiopereira.bankaccount.domain.model;

import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
import com.fbiopereira.bankaccount.domain.model.Account;
import java.util.HashSet;
import java.util.Set;
import static com.fbiopereira.bankaccount.domain.enums.AccountErrorMessages.ACCOUNT_DOES_NOT_EXIST;



public class Bank {


    private final Set<Account> accounts;

    public Bank() {
        accounts = new HashSet<>();
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

}



