package com.fbiopereira.bankaccount.service;

import com.fbiopereira.bankaccount.domain.enums.TransferAccountType;
import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
import com.fbiopereira.bankaccount.domain.model.Account;
import com.fbiopereira.bankaccount.dto.BankOperationsRequest;
import com.fbiopereira.bankaccount.dto.BankOperationsResponse;
import com.fbiopereira.bankaccount.usecases.BankOperationsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BankOperationsService {

    @Autowired
    BankOperationsImpl bankOperations;

    public void resetBank() {
        bankOperations.resetBank();
    }

    public ResponseEntity<Integer> balance(String accountId)  {

        try {
            Account account = bankOperations.findAccountByID(accountId);
            return ResponseEntity.status(HttpStatus.OK).body(account.getBalance());
        }
        catch (AccountNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }

    }

    public ResponseEntity<Object> deposit(BankOperationsRequest bankOperationsRequest) {

        Account account = bankOperations.deposit(bankOperationsRequest.getDestination(), bankOperationsRequest.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BankOperationsResponse(null, account));

    }

    public ResponseEntity<Object> withdraw(BankOperationsRequest bankOperationsRequest){

        try{
            Account account = bankOperations.withdraw(bankOperationsRequest.getOrigin(), bankOperationsRequest.getAmount());
            return ResponseEntity.status(HttpStatus.CREATED).body(new BankOperationsResponse(account, null));
        }
        catch (AccountNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }

    }

    public ResponseEntity<Object> transfer(BankOperationsRequest bankOperationsRequest){

        try{
            Map<TransferAccountType,Account> returnMap = bankOperations.transfer(bankOperationsRequest.getOrigin(), bankOperationsRequest.getDestination(), bankOperationsRequest.getAmount());

            return ResponseEntity.status(HttpStatus.CREATED).body(new BankOperationsResponse(returnMap.get(TransferAccountType.origin), returnMap.get(TransferAccountType.destination)));
        }
        catch (AccountNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }

    }

}
