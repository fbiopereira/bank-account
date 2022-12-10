package com.fbiopereira.bankaccount.bdd.steps;

import com.fbiopereira.bankaccount.service.BankOperationsService;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class BankSteps {

    @Autowired
    BankOperationsService bankOperationsService;


    @Given("I create an account with {string} and amount {int}")
    public void json_body(String accountId, int amount) {
      bankOperationsService.deposit(accountId, amount);
    }

}
