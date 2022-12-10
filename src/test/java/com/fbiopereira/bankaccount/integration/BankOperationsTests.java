package com.fbiopereira.bankaccount.integration;

import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
import com.fbiopereira.bankaccount.domain.model.Account;
import com.fbiopereira.bankaccount.usecases.BankOperationsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BankOperationsTests {

    @Autowired
    BankOperationsImpl bankOperationsImpl;

    @BeforeEach
    public void clearBank() {
        bankOperationsImpl.resetBank();
    }

    @Test
    public void resetBankTest() {

        bankOperationsImpl.deposit("100", 100);
        bankOperationsImpl.deposit("200", 100);
        bankOperationsImpl.deposit("300", 100);

        assertEquals(3, bankOperationsImpl.getBank().getAccounts().size());

        bankOperationsImpl.resetBank();

        assertEquals(0, bankOperationsImpl.getBank().getAccounts().size());

    }

    @Test
    public void depositTest() {

        bankOperationsImpl.deposit("100", 100);
        assertEquals(1, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(100, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[0].getBalance());

        bankOperationsImpl.deposit("100", 200);
        assertEquals(1, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(300, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[0].getBalance());

        bankOperationsImpl.deposit("10", 50);
        assertEquals(2, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(50, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[1].getBalance());

    }

    @Test
    public void withDrawTestSuccess() {

        bankOperationsImpl.deposit("100", 100);
        assertEquals(1, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(100, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        bankOperationsImpl.withdraw("100", 100);
        assertEquals(1, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(0, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[0].getBalance());


        bankOperationsImpl.withdraw("100", 200);
        assertEquals(1, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(-200, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[0].getBalance());


    }

    @Test
    public void withDrawTestMultipleAccountsSuccess() {

        Account accountToCheck;
        bankOperationsImpl.deposit("100", 100);
        assertEquals(1, bankOperationsImpl.getBank().getAccounts().size());
        accountToCheck = bankOperationsImpl.findAccountByID("100");
        assertEquals(100, accountToCheck.getBalance());

        bankOperationsImpl.deposit("50", 50);
        assertEquals(2, bankOperationsImpl.getBank().getAccounts().size());
        accountToCheck = bankOperationsImpl.findAccountByID("50");
        assertEquals(50, accountToCheck.getBalance());

        bankOperationsImpl.deposit("200", 200);
        assertEquals(3, bankOperationsImpl.getBank().getAccounts().size());
        accountToCheck = bankOperationsImpl.findAccountByID("200");
        assertEquals(200, accountToCheck.getBalance());

        bankOperationsImpl.withdraw("100", 100);
        bankOperationsImpl.withdraw("50", 100);


        accountToCheck = bankOperationsImpl.findAccountByID("100");
        assertEquals(0, accountToCheck.getBalance());
        accountToCheck = bankOperationsImpl.findAccountByID("50");
        assertEquals(-50, accountToCheck.getBalance());
        accountToCheck = bankOperationsImpl.findAccountByID("200");
        assertEquals(200, accountToCheck.getBalance());

    }

    @Test
    public void withDrawTestAccountNotFound() {

        assertThrows(AccountNotFoundException.class, () -> {
            bankOperationsImpl.withdraw("10", 50);
            ;
        });
        assertEquals(0, bankOperationsImpl.getBank().getAccounts().size());


    }

    @Test
    public void TransferTestSuccess() {

        bankOperationsImpl.deposit("100", 200);
        bankOperationsImpl.deposit("200", 0);

        assertEquals(2, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(200, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(0, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[1].getBalance());

        bankOperationsImpl.transfer("100", "200", 100);
        assertEquals(2, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(100, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(100, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[1].getBalance());
    }

    @Test
    public void TransferTestSourceAccountNotFound() {

        bankOperationsImpl.deposit("100", 200);
        bankOperationsImpl.deposit("200", 0);

        assertEquals(2, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(200, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(0, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[1].getBalance());

        assertThrows(AccountNotFoundException.class, () -> {
            bankOperationsImpl.transfer("10", "200", 100);
            ;
            ;
        });

        assertEquals(2, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(200, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(0, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[1].getBalance());
    }

    @Test
    public void TransferTestDestinationAccountNotFound() {

        bankOperationsImpl.deposit("100", 200);
        bankOperationsImpl.deposit("200", 0);

        assertEquals(2, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(200, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(0, bankOperationsImpl.getBank().getAccounts().toArray(new Account[0])[1].getBalance());

        bankOperationsImpl.transfer("100", "20", 10);

        Account account1, account2, account3;
        account1 = bankOperationsImpl.findAccountByID("100");
        account2 = bankOperationsImpl.findAccountByID("200");
        account3 = bankOperationsImpl.findAccountByID("20");
        assertEquals(3, bankOperationsImpl.getBank().getAccounts().size());
        assertEquals(190, account1.getBalance());
        assertEquals(0, account2.getBalance());
        assertEquals(10, account3.getBalance());

    }
}
