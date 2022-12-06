package com.fbiopereira.bankaccount.usecases;

import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
import com.fbiopereira.bankaccount.domain.model.Account;
import com.fbiopereira.bankaccount.domain.model.Bank;

import java.util.Set;

import static com.fbiopereira.bankaccount.domain.enums.AccountErrorMessages.ACCOUNT_DOES_NOT_EXIST;

public class BankOperations {

   private Bank bank;

    public BankOperations(){
        this.bank = new Bank();
    }

    public void resetBank(){
        this.bank = new Bank();
    }

    public Account findAccount(int id) throws AccountNotFoundException {

        Account accountFound = bank.getAccounts().stream().filter(account -> id == account.getId())
                .findFirst()
                .orElse(null);

        if (accountFound == null) {
            throw new AccountNotFoundException(ACCOUNT_DOES_NOT_EXIST.getCode(), ACCOUNT_DOES_NOT_EXIST.getMessage());
        }

        return accountFound;

    }

    public void saveAccount(Account account){
        this.bank.getAccounts().remove(account);
        this.bank.getAccounts().add(account);
    }



    public void transfer(int sourceAccountId, int destinationAccountId, int amount){

    }

    public Bank getBank() {
        return bank;
    }

}
