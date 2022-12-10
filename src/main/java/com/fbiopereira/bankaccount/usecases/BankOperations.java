package com.fbiopereira.bankaccount.usecases;

import com.fbiopereira.bankaccount.domain.enums.TransferAccountType;
import com.fbiopereira.bankaccount.domain.model.Account;

import java.util.Map;

public interface BankOperations {

    public Account deposit(String accountId, int amount);
    public Account withdraw(String accountId, int amount);
    public Map<TransferAccountType, Account> transfer(String sourceAccountId, String destinationAccountId, int amount);
    public void saveAccount(Account account);
    public Account findAccountByID(String id);
    public void resetBank();
}
