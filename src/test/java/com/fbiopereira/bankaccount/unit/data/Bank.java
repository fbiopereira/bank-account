package com.fbiopereira.bankaccount.unit.data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class Bank {

    @Test
    public void BankCreation(){

        com.fbiopereira.bankaccount.data.memory.Bank bank = new com.fbiopereira.bankaccount.data.memory.Bank();
        assertEquals(0, bank.getAccounts().size());

    }

}
