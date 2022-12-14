package com.fbiopereira.bankaccount.unit.model;
import static org.junit.jupiter.api.Assertions.*;
import com.fbiopereira.bankaccount.domain.enums.OperationType;
import com.fbiopereira.bankaccount.domain.exceptions.InvalidOperationException;
import com.fbiopereira.bankaccount.domain.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountTest {

    @Test
    void AccountCreation(){

        Account account = new Account("100");
        assertEquals("100", account.getId());
        assertEquals(0, account.getBalance());

       assertThrows(InvalidOperationException.class, () -> {
            new Account(null);
        });

       assertThrows(InvalidOperationException.class, () -> {
            new Account(null);
        });
    }

    @Test
    void AccountOperation(){

        Account account = new Account("100");
        account.doOperation(100, OperationType.deposit);
        assertEquals(100, account.getBalance());
        account.doOperation(100, OperationType.withdraw);
        assertEquals(0, account.getBalance());
        account.doOperation(100, OperationType.withdraw);
        assertEquals(-100, account.getBalance());

        assertThrows(InvalidOperationException.class, () -> {
            account.doOperation(100, OperationType.transfer);
        });

    }


}
