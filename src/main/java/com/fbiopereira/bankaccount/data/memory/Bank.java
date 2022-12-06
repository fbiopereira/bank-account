package com.fbiopereira.bankaccount.data.memory;

import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
import com.fbiopereira.bankaccount.domain.model.Account;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.fbiopereira.bankaccount.domain.enums.AccountErrorMessages.ACCOUNT_DOES_NOT_EXIST;



public class Bank {


    private Set<Account> accounts;

    public Bank() {
        accounts = new HashSet<>();
    }

    public Account findAccount(int id) throws AccountNotFoundException {

        Account accountFound = getAccounts().stream().filter(account -> id == account.getId())
                .findFirst()
                .orElse(null);

        if (accountFound == null) {
            throw new AccountNotFoundException(ACCOUNT_DOES_NOT_EXIST.getCode(), ACCOUNT_DOES_NOT_EXIST.getMessage());
        }

        return accountFound;

    }

    private Account findAccountPosition(int id) throws AccountNotFoundException {

        Account accountFound = getAccounts().stream().filter(account -> id == account.getId())
                .findFirst()
                .orElse(null);

        if (accountFound == null) {
            throw new AccountNotFoundException(ACCOUNT_DOES_NOT_EXIST.getCode(), ACCOUNT_DOES_NOT_EXIST.getMessage());
        }

        return accountFound;

    }

    public void saveAccount(Account account){

        try {
            if (findAccount(account.getId()) != null){
                this.accounts.remove(account);
            }
        }
        catch (AccountNotFoundException exception){
            //TODO: Improve this logic
        }

        this.accounts.add(account);






    }

    public Set<Account> getAccounts() {
        return accounts;
    }
}



