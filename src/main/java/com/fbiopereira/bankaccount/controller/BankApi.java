package com.fbiopereira.bankaccount.controller;

import com.fbiopereira.bankaccount.domain.enums.TransferAccountType;
import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
import com.fbiopereira.bankaccount.domain.model.Account;
import com.fbiopereira.bankaccount.dto.*;
import com.fbiopereira.bankaccount.service.BankOperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/bank")
public class BankApi {

    @Autowired
    BankOperationsService bankOperations;

    @PostMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Resets all accounts in our bank", responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = String.class)))})
    public ResponseEntity<String> reset() {

        bankOperations.resetBank();
        return ResponseEntity.status(HttpStatus.OK).body("OK");

    }

    @PostMapping(path = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Account Operations", responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = BankOperationsDepositWithdrawResponse.class)))})
    public ResponseEntity<Object> event(@RequestBody @Valid BankOperationsRequest bankOperationsRequest)  {

        Account account;

        switch (bankOperationsRequest.getType()){
            case deposit:
                account = bankOperations.deposit(bankOperationsRequest.getDestination(), bankOperationsRequest.getAmount());
                return ResponseEntity.status(HttpStatus.CREATED).body(new BankOperationsDepositWithdrawResponse(account));
            case withdraw:
                try{
                    account = bankOperations.withdraw(bankOperationsRequest.getDestination(), bankOperationsRequest.getAmount());
                    return ResponseEntity.status(HttpStatus.CREATED).body(new BankOperationsDepositWithdrawResponse(account));
                }
                catch (AccountNotFoundException exception){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
                }
            case transfer:
                try{
                    BankOperationsTransferRequest bankOperationsTransferRequest = (BankOperationsTransferRequest) bankOperationsRequest;
                    Map<TransferAccountType,Account> returnMap = bankOperations.transfer(bankOperationsTransferRequest.getOrigin(), bankOperationsTransferRequest.getDestination(), bankOperationsTransferRequest.getAmount());

                    return ResponseEntity.status(HttpStatus.CREATED).body(new BankOperationsTransferResponse(returnMap.get(TransferAccountType.origin), returnMap.get(TransferAccountType.destination)));
                }
                catch (AccountNotFoundException exception){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
                }
        }

        return null;

    }
}
