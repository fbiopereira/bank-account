package com.fbiopereira.bankaccount.controller;

import com.fbiopereira.bankaccount.dto.BankOperationsRequest;
import com.fbiopereira.bankaccount.dto.BankOperationsResponse;
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
    @Operation(summary = "Account Operations", responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = BankOperationsResponse.class)))})
    public ResponseEntity<BankOperationsResponse> event(@RequestBody @Valid BankOperationsRequest bankOperationsRequest)  {



        return null;

    }
}
