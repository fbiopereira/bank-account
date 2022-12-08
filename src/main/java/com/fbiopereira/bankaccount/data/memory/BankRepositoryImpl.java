package com.fbiopereira.bankaccount.data.memory;

import com.fbiopereira.bankaccount.data.repository.AccountRepository;
import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
import com.fbiopereira.bankaccount.domain.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static com.fbiopereira.bankaccount.domain.enums.AccountErrorMessages.ACCOUNT_DOES_NOT_EXIST;

@Component
public class BankRepositoryImpl implements AccountRepository {

    @Autowired
    Bank bank;

    @Override
    public Object save(Account account) {
        this.bank.getAccounts().remove(account);
        this.bank.getAccounts().add(account);
        return bank;
    }

    @Override
    public Account findAccountById(int accountId) {

        Account accountFound = bank.getAccounts().stream().filter(account -> accountId == account.getId())
                .findFirst()
                .orElse(null);

        if (accountFound == null) {
            throw new AccountNotFoundException(ACCOUNT_DOES_NOT_EXIST.getCode(), ACCOUNT_DOES_NOT_EXIST.getMessage());
        }
        return accountFound;
    }

    @Override
    public Object deleteAll() {
        bank.getAccounts().clear();
        return bank;
    }
}
