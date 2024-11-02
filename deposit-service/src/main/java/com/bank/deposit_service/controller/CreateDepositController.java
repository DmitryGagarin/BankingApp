package com.bank.deposit_service.controller;

import com.bank.deposit_service.model.Deposit;
import com.bank.deposit_service.repository.DepositRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "CreateDepositController", description = "Allows to create a new deposit")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CreateDepositController {

    @Autowired
    private DepositRepository depositRepository;

    protected static UUID currentUserId;

    @Operation(
            summary = "Registers new deposit for a user",
            description = "We identify userId using a react app, passing it as arguments and creating new deposit"
    )
    @PostMapping("/create_deposit/{userId}/{type}")
    public ResponseEntity<String> createDeposit(@PathVariable UUID userId,
                                                @PathVariable Deposit.DepositType type) {

        Logger logger = LoggerFactory.getLogger(CreateDepositController.class);
        logger.info("Creating new deposit");
        Deposit deposit = new Deposit(userId, type);
        currentUserId = userId;
        logger.info("New deposit saved");
        depositRepository.save(deposit);
        logger.info("CURRENT USER ID: " + currentUserId);
        return ResponseEntity.status(HttpStatus.OK).body("Deposit saved successfully");
    }
}
