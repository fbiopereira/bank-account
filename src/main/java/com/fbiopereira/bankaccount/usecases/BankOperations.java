package com.fbiopereira.bankaccount.usecases;

import com.fbiopereira.bankaccount.domain.enums.TransferAccountType;
import com.fbiopereira.bankaccount.domain.model.Account;

import java.util.Map;

public interface BankOperations {

    public Account deposit(int accountId, int amount);
    public Account withdraw(int accountId, int amount);
    public Map<TransferAccountType, Account> transfer(int sourceAccountId, int destinationAccountId, int amount);
    public void saveAccount(Account account);
    public Account findAccountByID(int id);
    public void resetBank();
}
