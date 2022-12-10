package com.fbiopereira.bankaccount.bdd.steps;

import com.fbiopereira.bankaccount.usecases.BankOperationsImpl;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class BankSteps {

    @Autowired
    BankOperationsImpl bankOperationsImpl;


    @Given("I create an account with {string} and amount {int}")
    public void json_body(String accountId, int amount) {
      bankOperationsImpl.deposit(accountId, amount);
    }

}
