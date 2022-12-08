package com.fbiopereira.bankaccount.unitTests.data;

import com.fbiopereira.bankaccount.data.memory.BankRepositoryImpl;
import com.fbiopereira.bankaccount.domain.enums.OperationType;
import com.fbiopereira.bankaccount.domain.model.Account;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fbiopereira.bankaccount.data.memory.Bank;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankRepositoryImplMemoryTest {

    BankRepositoryImpl bankRepositoryInMemory;

    @BeforeEach
    public void setup() {
        bankRepositoryInMemory = new BankRepositoryImpl();
    }

    @Test
    public void BankSaveOneAccount(){

        Account account = new Account(100);
        Bank bank = (Bank) bankRepositoryInMemory.save(account);
        assertEquals(1, bank.getAccounts().size());
    }

    @Test
    public void BankSaveSameAccountMultipleTimes(){

        BankRepositoryImpl bankRepositoryInMemory = new BankRepositoryImpl();

        Account account = new Account(100);
        Bank bank = (Bank) bankRepositoryInMemory.save(account);
        assertEquals(1, bank.getAccounts().size());

        bank = (Bank) bankRepositoryInMemory.save(account);
        assertEquals(1, bank.getAccounts().size());

        bank = (Bank) bankRepositoryInMemory.save(account);
        assertEquals(1, bank.getAccounts().size());

    }


    @Test
    public void BankSaveSameAccountMultipleTimesWithUpdatedBalance(){

        BankRepositoryImpl bankRepositoryInMemory = new BankRepositoryImpl();

        Account account = new Account(100);
        account.doOperation(50, OperationType.deposit);

        Bank bank = (Bank) bankRepositoryInMemory.save(account);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(50, bank.getAccounts().toArray(new Account[0])[0].getBalance());


        account.doOperation(100, OperationType.deposit);
        bankRepositoryInMemory.save(account);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(150, bank.getAccounts().toArray(new Account[0])[0].getBalance());

        account.doOperation(200, OperationType.withdraw);
        bankRepositoryInMemory.save(account);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(-50, bank.getAccounts().toArray(new Account[0])[0].getBalance());

    }

}
