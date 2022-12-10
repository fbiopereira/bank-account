package com.fbiopereira.bankaccount.service;
import com.fbiopereira.bankaccount.domain.enums.TransferAccountType;
import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
import com.fbiopereira.bankaccount.domain.model.Account;
import com.fbiopereira.bankaccount.data.memory.Bank;
import com.fbiopereira.bankaccount.usecases.BankOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

import static com.fbiopereira.bankaccount.domain.enums.AccountErrorMessages.ACCOUNT_DOES_NOT_EXIST;
import static com.fbiopereira.bankaccount.domain.enums.OperationType.deposit;
import static com.fbiopereira.bankaccount.domain.enums.OperationType.withdraw;
import static com.fbiopereira.bankaccount.domain.enums.TransferAccountType.destination;
import static com.fbiopereira.bankaccount.domain.enums.TransferAccountType.origin;

@Service
public class BankOperationsService implements BankOperations {


    @Autowired
    Bank bank;

    @Override
    public Account deposit(String accountId, int amount) {

        Account account;

        try{
            account = this.findAccountByID(accountId);

        }
        catch (AccountNotFoundException e){
            account = new Account(accountId);
      }

        account.doOperation(amount, deposit);
        this.saveAccount(account);
        return account;
    }

    @Override
    public Account withdraw(String accountId, int amount) {
        try {
            Account account = this.findAccountByID(accountId);
            account.doOperation(amount, withdraw);
            this.saveAccount(account);
            return account;
        } catch (AccountNotFoundException e) {
            throw new AccountNotFoundException(ACCOUNT_DOES_NOT_EXIST.getCode(), ACCOUNT_DOES_NOT_EXIST.getMessage());
        }
    }


    @Override
    public Map<TransferAccountType, Account> transfer(String sourceAccountId, String destinationAccountId, int amount) {
        try {
            Account originAccount = this.withdraw(sourceAccountId, amount);
            Account destinationAccount = this.deposit(destinationAccountId, amount);

            Map<TransferAccountType,Account> returnMap = new HashMap<>();

            returnMap.put(origin, originAccount);
            returnMap.put(destination, destinationAccount);

            return returnMap;


        } catch (AccountNotFoundException e) {
            throw new AccountNotFoundException(ACCOUNT_DOES_NOT_EXIST.getCode(), ACCOUNT_DOES_NOT_EXIST.getMessage());
        }

    }

    @Override
    public void saveAccount(Account account) {
        this.bank.getAccounts().remove(account);
        this.bank.getAccounts().add(account);
    }

    @Override
    public Account findAccountByID(String accountId) {

        Account accountFound = bank.getAccounts().stream().filter(account -> accountId.equals(account.getId()))
                .findFirst()
                .orElse(null);

        if (accountFound == null) {
            throw new AccountNotFoundException(ACCOUNT_DOES_NOT_EXIST.getCode(), ACCOUNT_DOES_NOT_EXIST.getMessage());
        }
        return accountFound;
    }

    @Override
    public void resetBank() {
        bank.getAccounts().clear();
    }

    public Bank getBank() {
        return bank;
    }
}
