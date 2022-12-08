package com.fbiopereira.bankaccount.integrationtests;

import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
import com.fbiopereira.bankaccount.domain.model.Account;
import com.fbiopereira.bankaccount.service.BankOperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BankOperationsTests {

    @Autowired
    BankOperationsService bankOperationsService;

    @BeforeEach
    public void clearBank() {
        bankOperationsService.resetBank();
    }

    @Test
    public void resetBankTest() {

        bankOperationsService.deposit(100, 100);
        bankOperationsService.deposit(200, 100);
        bankOperationsService.deposit(300, 100);

        assertEquals(3, bankOperationsService.getBank().getAccounts().size());

        bankOperationsService.resetBank();

        assertEquals(0, bankOperationsService.getBank().getAccounts().size());

    }

    @Test
    public void depositTest() {

        bankOperationsService.deposit(100, 100);
        assertEquals(1, bankOperationsService.getBank().getAccounts().size());
        assertEquals(100, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());

        bankOperationsService.deposit(100, 200);
        assertEquals(1, bankOperationsService.getBank().getAccounts().size());
        assertEquals(300, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());

        bankOperationsService.deposit(10, 50);
        assertEquals(2, bankOperationsService.getBank().getAccounts().size());
        assertEquals(50, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[1].getBalance());

    }

    @Test
    public void withDrawTestSuccess() {

        bankOperationsService.deposit(100, 100);
        assertEquals(1, bankOperationsService.getBank().getAccounts().size());
        assertEquals(100, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        bankOperationsService.withdraw(100, 100);
        assertEquals(1, bankOperationsService.getBank().getAccounts().size());
        assertEquals(0, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());


        bankOperationsService.withdraw(100, 200);
        assertEquals(1, bankOperationsService.getBank().getAccounts().size());
        assertEquals(-200, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());


    }

    @Test
    public void withDrawTestMultipleAccountsSuccess() {

        bankOperationsService.deposit(100, 100);
        assertEquals(1, bankOperationsService.getBank().getAccounts().size());
        assertEquals(100, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());

        bankOperationsService.deposit(50, 50);
        assertEquals(2, bankOperationsService.getBank().getAccounts().size());
        assertEquals(50, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());

        bankOperationsService.deposit(200, 200);
        assertEquals(3, bankOperationsService.getBank().getAccounts().size());
        assertEquals(200, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[2].getBalance());

        bankOperationsService.withdraw(100, 100);
        bankOperationsService.withdraw(50, 100);

        assertEquals(0, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[1].getBalance());
        assertEquals(-50, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(200, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[2].getBalance());

    }

    @Test
    public void withDrawTestAccountNotFound() {

        assertThrows(AccountNotFoundException.class, () -> {
            bankOperationsService.withdraw(10, 50);
            ;
        });
        assertEquals(1, bankOperationsService.getBank().getAccounts().size());


    }

    @Test
    public void TransferTestSuccess() {

        bankOperationsService.deposit(100, 200);
        bankOperationsService.deposit(200, 0);

        assertEquals(2, bankOperationsService.getBank().getAccounts().size());
        assertEquals(200, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(0, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[1].getBalance());

        bankOperationsService.transfer(100, 200, 100);
        assertEquals(2, bankOperationsService.getBank().getAccounts().size());
        assertEquals(100, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(100, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[1].getBalance());
    }

    @Test
    public void TransferTestSourceAccountNotFound() {

        bankOperationsService.deposit(100, 200);
        bankOperationsService.deposit(200, 0);

        assertEquals(2, bankOperationsService.getBank().getAccounts().size());
        assertEquals(200, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(0, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[1].getBalance());

        assertThrows(AccountNotFoundException.class, () -> {
            bankOperationsService.transfer(10, 200, 100);
            ;
            ;
        });

        assertEquals(2, bankOperationsService.getBank().getAccounts().size());
        assertEquals(200, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(0, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[1].getBalance());
    }

    @Test
    public void TransferTestDestinationAccountNotFound() {

        bankOperationsService.deposit(100, 200);
        bankOperationsService.deposit(200, 0);

        assertEquals(2, bankOperationsService.getBank().getAccounts().size());
        assertEquals(200, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[0].getBalance());
        assertEquals(0, bankOperationsService.getBank().getAccounts().toArray(new Account[0])[1].getBalance());

        bankOperationsService.transfer(100, 20, 10);

        Account account1, account2, account3;
        account1 = bankOperationsService.findAccountByID(100);
        account2 = bankOperationsService.findAccountByID(200);
        account3 = bankOperationsService.findAccountByID(20);
        assertEquals(3, bankOperationsService.getBank().getAccounts().size());
        assertEquals(190, account1.getBalance());
        assertEquals(0, account2.getBalance());
        assertEquals(10, account3.getBalance());

    }
}
