package com.bank.user_service.config;

import com.bank.user_service.model.User;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;

@Configuration
public class RabbitMQConfig {
    public final static String QUEUE_NAME = "USER_ID_QUEUE";
    public final static String HOST_NAME = "rabbitmq";
    private final static Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    public static ResponseEntity<?> sendCurrentUserToQueue(User currentUser) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQConfig.HOST_NAME);

        try (Connection connection = factory.newConnection()) {
            logger.info("Creating channel");
            Channel channel = connection.createChannel();
            logger.info("Queue Declaring started");
            channel.queueDeclare(RabbitMQConfig.QUEUE_NAME, false, false, false, null);
            logger.info("Generating a message");

            String message = String.valueOf(currentUser.getId());
            logger.info("Basic Publish");
            channel.basicPublish("", RabbitMQConfig.QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            logger.info("UUID of current user was sent: " + message);
        } catch (Exception e) {
            logger.error("Failed to send a message via RabbitMQ", e);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Current user id was sent");
    }
}
