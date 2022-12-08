package com.fbiopereira.bankaccount.data.memory;

import com.fbiopereira.bankaccount.domain.model.Account;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;



@Component
@Scope("singleton")
public class Bank {


    private final Set<Account> accounts;

    public Bank() {
        accounts = new HashSet<>();
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

}



