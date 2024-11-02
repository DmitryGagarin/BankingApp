// TODO: tests

package com.bank.deposit_service.controller;

import com.bank.deposit_service.config.RabbitMQConfig;
import com.bank.deposit_service.model.Deposit;
import com.bank.deposit_service.repository.DepositRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin("/localhost:3000")
public class ShowDepositController {

    @Autowired
    private DepositRepository depositRepository;
    private UUID currentUserId;
    private final static Logger logger = LoggerFactory.getLogger(ShowDepositController.class);

    @Operation(
            summary = "Fetches value of rabbitMQ queue to get current user id",
            description = "User-service sent current user data to queue. This method takes this value and passes it variables"
    )
    private ResponseEntity<?> receiveUserFromQueue() {
        try {
            logger.info("Rabbit MQ factory started");
            ConnectionFactory factory = new ConnectionFactory();
            logger.info("Host is set");
            factory.setHost(RabbitMQConfig.HOST_NAME);
            logger.info("Connection is set");
            Connection connection = factory.newConnection();
            logger.info("Channel is set");
            Channel channel = connection.createChannel();
            logger.info("Queue Declaration");
            channel.queueDeclare(RabbitMQConfig.QUEUE_NAME, false, false, false, null);

            logger.info("Delivery Callback process started");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                logger.info("Receiving a message");
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                logger.info("Transforming message from string to uuid");
                currentUserId = UUID.fromString(message);
                logger.info("Message with current userId: " + currentUserId);
            };
            logger.info("Basic consume");
            channel.basicConsume(RabbitMQConfig.QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            logger.error("Failed to receive a message from Rabbit");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Received user data from RabbitMQ");
    }

    @Operation(
            summary = "Shows current user all open deposits",
            description = "Takes value from queue, and returns all deposits of this user. Further, frontend modifies this data into a table"
    )
    @GetMapping("/all_deposits")
    public List<Deposit> getAllDeposits() throws Exception {

        logger.info("Get All Deposits controller started");

        logger.info("Started process of receiving from RabbitMQ");

        receiveUserFromQueue();
//        try {
//            logger.info("Rabbit MQ factory started");
//            ConnectionFactory factory = new ConnectionFactory();
//            logger.info("Host is set");
//            factory.setHost(HOST_NAME);
//            logger.info("Connection is set");
//            Connection connection = factory.newConnection();
//            logger.info("Channel is set");
//            Channel channel = connection.createChannel();
//            logger.info("Queue Declaration");
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//
//            logger.info("Delivery Callback process started");
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                logger.info("Receiving a message");
//                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                logger.info("Transforming message from string to uuid");
//                currentUserId = UUID.fromString(message);
//                logger.info("Message with current userId: " + currentUserId);
//            };
//            logger.info("Basic consume");
//            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
//        } catch (Exception e) {
//            logger.error("Failed to receive a message from Rabbit");
//        }

        return depositRepository.findByUserId(currentUserId);
    }
}
