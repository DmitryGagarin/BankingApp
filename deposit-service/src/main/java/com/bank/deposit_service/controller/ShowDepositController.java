// TODO: tests

package com.bank.deposit_service.controller;

import com.bank.deposit_service.config.RabbitMQConfig;
import com.bank.deposit_service.model.Deposit;
import com.bank.deposit_service.model.UserIdWrapper;
import com.bank.deposit_service.repository.DepositRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin("/localhost:3000")
public class ShowDepositController {

    @Autowired
    private DepositRepository depositRepository;
    private UUID currentUserId = null;
    private final static Logger logger = LoggerFactory.getLogger(ShowDepositController.class);

    @Operation(
            summary = "Shows current user all open deposits",
            description = "Takes value from queue, and returns all deposits of this user. Further, frontend modifies this data into a table"
    )
    @GetMapping("/all_deposits")
    @Cacheable(value = "depositsCache", key = "#currentUserId")
    public List<Deposit> getAllDeposits() throws Exception {

        logger.info("Get All Deposits controller started");
        logger.info("Started process of receiving from RabbitMQ");

        UserIdWrapper userIdWrapper = new UserIdWrapper(currentUserId);
        RabbitMQConfig.receiveUserFromQueue(userIdWrapper);
        currentUserId = userIdWrapper.getUserId();

        return depositRepository.findByUserId(currentUserId);
    }
}
