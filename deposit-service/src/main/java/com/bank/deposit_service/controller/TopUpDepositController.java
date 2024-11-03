package com.bank.deposit_service.controller;

import com.bank.deposit_service.config.RabbitMQConfig;
import com.bank.deposit_service.model.Deposit;
import com.bank.deposit_service.model.UserIdWrapper;
import com.bank.deposit_service.repository.DepositRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin("/localhost:3000")
public class TopUpDepositController {

    @Autowired
    private DepositRepository depositRepository;

    private UUID currentUserId = null;
    private final static Logger logger = LoggerFactory.getLogger(TopUpDepositController.class);

    @PostMapping("/top-up-deposit/{depositId}/{amount}")
    @Operation(
            summary = "Allows to add money for the deposit",
            description = "Takes depositId and amount from url, finds deposit, updates amount of money on deposit"
    )
    private ResponseEntity<?> topUpDeposit(@PathVariable UUID depositId, @PathVariable BigDecimal amount) throws Exception {

        logger.info("Started process of getting currentUserId via RabbitMQ");
        try {
            UserIdWrapper userIdWrapper = new UserIdWrapper(currentUserId);
            RabbitMQConfig.receiveUserFromQueue(userIdWrapper);
            logger.info("CurrentUserId is got");
            currentUserId = userIdWrapper.getUserId();
        } catch (Exception e) {
            logger.error("Process of getting currentUserId failed");
        }

        logger.info("Generating optional depositToTopUpOptional");
        Optional<Deposit> depositToTopUpOptional = depositRepository.findById(depositId);
        logger.info("depositToTopUpOptional value is: " + depositToTopUpOptional);
        if (depositToTopUpOptional.isPresent()) {
            logger.info("depositToTopUpOptional isPresent");
            Deposit depositToTopUp = depositToTopUpOptional.get();

            if (!depositToTopUp.getUserId().equals(currentUserId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: deposit userId != currentUserId");
            }

            depositToTopUp.setAmount(depositToTopUp.getAmount().add(amount));
            return ResponseEntity.status(HttpStatus.OK).body("Amount on deposit is updated");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This deposit not found");
        }
    }
}