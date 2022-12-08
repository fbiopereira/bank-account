package com.fbiopereira.bankaccount.service;
import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
import com.fbiopereira.bankaccount.domain.model.Account;
import com.fbiopereira.bankaccount.data.memory.Bank;
import com.fbiopereira.bankaccount.usecases.BankOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.fbiopereira.bankaccount.domain.enums.AccountErrorMessages.ACCOUNT_DOES_NOT_EXIST;
import static com.fbiopereira.bankaccount.domain.enums.OperationType.deposit;
import static com.fbiopereira.bankaccount.domain.enums.OperationType.withdraw;

@Service
public class BankOperationsService implements BankOperations {


    @Autowired
    Bank bank;

    @Override
    public void deposit(int accountId, int amount) {

        Account account;

        try{
            account = this.findAccountByID(accountId);

        }
        catch (AccountNotFoundException e){
            account = new Account(accountId);
      }

        account.doOperation(amount, deposit);
        this.saveAccount(account);

    }

    @Override
    public void withdraw(int accountId, int amount) {
        try {
            Account account = this.findAccountByID(accountId);
            account.doOperation(amount, withdraw);
            this.saveAccount(account);
        } catch (AccountNotFoundException e) {
            throw new AccountNotFoundException(ACCOUNT_DOES_NOT_EXIST.getCode(), ACCOUNT_DOES_NOT_EXIST.getMessage());
        }
    }


    @Override
    public void transfer(int sourceAccountId, int destinationAccountId, int amount) {
        try {
            this.withdraw(sourceAccountId, amount);
            this.deposit(destinationAccountId, amount);
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
    public Account findAccountByID(int accountId) {

        Account accountFound = bank.getAccounts().stream().filter(account -> accountId == account.getId())
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
