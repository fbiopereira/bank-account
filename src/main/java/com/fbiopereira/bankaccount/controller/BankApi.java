package com.fbiopereira.bankaccount.controller;

import com.fbiopereira.bankaccount.data.memory.Bank;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
public class BankApi {

    @Autowired
    Bank bank;

    @PostMapping(path = "/reset", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Resets all accounts in our bank", responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = String.class)))})
    public ResponseEntity<String> reset() {

        bank.getAccounts().clear();
        return ResponseEntity.status(HttpStatus.OK).body("OK");

    }
}
