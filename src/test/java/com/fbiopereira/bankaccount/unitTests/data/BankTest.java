package com.fbiopereira.bankaccount.unitTests.data;

import com.fbiopereira.bankaccount.data.memory.Bank;
import com.fbiopereira.bankaccount.domain.enums.OperationType;
import com.fbiopereira.bankaccount.domain.model.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    @Test
    public void BankCreation(){

        Bank bank = new Bank();
        assertEquals(0, bank.getAccounts().size());

    }

    @Test
    public void BankSaveAccount(){

        Bank bank = new Bank();
        assertEquals(0, bank.getAccounts().size());
        Account account = new Account(100);
        bank.saveAccount(account);
        assertEquals(1, bank.getAccounts().size());

        Account account2 = new Account(100);
        account2.doOperation(100, OperationType.deposit);
        bank.saveAccount(account2);
        assertEquals(1, bank.getAccounts().size());

    }
}
