package com.fbiopereira.bankaccount.controller;

import com.fbiopereira.bankaccount.domain.exceptions.AccountNotFoundException;
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

@RestController
@RequestMapping("/")
public class BankApi {

    @Autowired
    BankOperationsService bankOperationsService;

    @GetMapping(path = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get the balance fron an account", responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = String.class)))})
    public ResponseEntity<Integer> getBalance(@RequestParam("account_id") String accountId) {

        return bankOperationsService.balance(accountId);

    }



    @PostMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Resets all accounts in our bank", responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = String.class)))})
    public ResponseEntity<String> reset() {

        bankOperationsService.resetBank();

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

        return switch (bankOperationsRequest.getType()) {
            case deposit -> bankOperationsService.deposit(bankOperationsRequest);
            case withdraw -> bankOperationsService.withdraw(bankOperationsRequest);
            case transfer -> bankOperationsService.transfer(bankOperationsRequest);
        };
    }
}
