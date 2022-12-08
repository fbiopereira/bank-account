package com.fbiopereira.bankaccount.unitTests.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bank {

    @Test
    public void BankCreation(){

        com.fbiopereira.bankaccount.data.memory.Bank bank = new com.fbiopereira.bankaccount.data.memory.Bank();
        assertEquals(0, bank.getAccounts().size());

    }






    @Test
    public void BankTransferOperation() {



    }
}
