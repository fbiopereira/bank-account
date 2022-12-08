package com.fbiopereira.bankaccount.usecases;

import com.fbiopereira.bankaccount.data.memory.Bank;

public class BankOperations {

   private Bank bank;

    public BankOperations(){
        this.bank = new Bank();
    }

    public Bank getBank() {
        return bank;
    }

}
