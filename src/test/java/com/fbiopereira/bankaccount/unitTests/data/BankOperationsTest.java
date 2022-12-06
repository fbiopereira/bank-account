package com.fbiopereira.bankaccount.unitTests.data;

import com.fbiopereira.bankaccount.domain.model.Bank;
import com.fbiopereira.bankaccount.domain.enums.OperationType;
import com.fbiopereira.bankaccount.domain.model.Account;
import com.fbiopereira.bankaccount.usecases.BankOperations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankOperationsTest {

    @Test
    public void BankCreation(){

        Bank bank = new Bank();
        assertEquals(0, bank.getAccounts().size());

    }

    @Test
    public void BankSaveOneAccount(){

        BankOperations bankOperations = new BankOperations();

        assertEquals(0, bankOperations.getBank().getAccounts().size());
        Account account = new Account(100);
        bankOperations.saveAccount(account);
        assertEquals(1, bankOperations.getBank().getAccounts().size());

        bankOperations.saveAccount(account);
        assertEquals(1, bankOperations.getBank().getAccounts().size());
    }

    @Test
    public void BankSaveSameAccountMultipleTimes(){

        Bank bank = new Bank();
        BankOperations bankOperations = new BankOperations(bank);

        assertEquals(0, bank.getAccounts().size());
        Account account = new Account(100);
        bankOperations.saveAccount(account);
        assertEquals(1, bank.getAccounts().size());

        bankOperations.saveAccount(account);
        assertEquals(1, bank.getAccounts().size());

        bankOperations.saveAccount(account);
        assertEquals(1, bank.getAccounts().size());

    }


    @Test
    public void BankSaveSameAccountMultipleTimesWithUpdatedBalance(){

        Bank bank = new Bank();
        BankOperations bankOperations = new BankOperations(bank);

        assertEquals(0, bank.getAccounts().size());
        Account account = new Account(100);
        bankOperations.saveAccount(account);
        assertEquals(1, bank.getAccounts().size());

        account.doOperation(50, OperationType.deposit);
        bankOperations.saveAccount(account);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(50, bank.getAccounts().toArray(new Account[0])[0].getBalance());

        account.doOperation(100, OperationType.deposit);
        bankOperations.saveAccount(account);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(150, bank.getAccounts().toArray(new Account[0])[0].getBalance());

        account.doOperation(200, OperationType.withdraw);
        bankOperations.saveAccount(account);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(-50, bank.getAccounts().toArray(new Account[0])[0].getBalance());

    }


    @Test
    public void BankTransferOperation() {



    }
}
