package com.fbiopereira.bankaccount.usecases;

import com.fbiopereira.bankaccount.domain.model.Account;

public interface BankOperations {

    public void deposit(int accountId, int amount);
    public void withdraw(int accountId, int amount);
    public void transfer(int sourceAccountId, int destinationAccountId, int amount);
    public void saveAccount(Account account);
    public Account findAccountByID(int id);
    public void resetBank();
}
