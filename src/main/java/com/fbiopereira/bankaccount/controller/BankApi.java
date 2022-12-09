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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Account Operations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deposit, WithDraw or Transfer Ok",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankOperationsResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Account not found")})
    public ResponseEntity<Object> event(@RequestBody @Valid BankOperationsRequest bankOperationsRequest)  {

        Account account;

        switch (bankOperationsRequest.getType()){
            case deposit:
                account = bankOperations.deposit(bankOperationsRequest.getDestination(), bankOperationsRequest.getAmount());
                return ResponseEntity.status(HttpStatus.CREATED).body(new BankOperationsResponse(null, account));
            case withdraw:
                try{
                    account = bankOperations.withdraw(bankOperationsRequest.getDestination(), bankOperationsRequest.getAmount());
                    return ResponseEntity.status(HttpStatus.CREATED).body(new BankOperationsResponse(account, null));
                }
                catch (AccountNotFoundException exception){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
                }
            case transfer:
                try{
                    Map<TransferAccountType,Account> returnMap = bankOperations.transfer(bankOperationsRequest.getOrigin(), bankOperationsRequest.getDestination(), bankOperationsRequest.getAmount());

                    return ResponseEntity.status(HttpStatus.CREATED).body(new BankOperationsResponse(returnMap.get(TransferAccountType.origin), returnMap.get(TransferAccountType.destination)));
                }
                catch (AccountNotFoundException exception){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
                }
        }

        return null;
    }
}
