package com.fbiopereira.bankaccount.unit.data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class BankTest {

    @Test
    void BankCreation(){

        com.fbiopereira.bankaccount.data.Bank bank = new com.fbiopereira.bankaccount.data.Bank();
        assertEquals(0, bank.getAccounts().size());

    }

}
