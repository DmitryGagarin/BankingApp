package com.bank.deposit_service.controller;

import com.bank.deposit_service.model.Deposit;
import com.bank.deposit_service.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CreateDepositController {

    @Autowired
    private DepositRepository depositRepository;

    protected static UUID currentUserId;

    @PostMapping("/create_deposit/{userId}/{type}")
    public ResponseEntity<String> createDeposit(@PathVariable UUID userId,
                                                @PathVariable Deposit.DepositType type) {
        Deposit deposit = new Deposit(userId, type);
        currentUserId = userId;
        depositRepository.save(deposit);
        return ResponseEntity.status(HttpStatus.OK).body("Deposit saved successfully");
    }
}
