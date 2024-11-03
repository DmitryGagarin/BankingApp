package com.bank.deposit_service.config;

import com.bank.deposit_service.model.UserIdWrapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


@Configuration
@Data
public class RabbitMQConfig {
    @Getter
    private final static String QUEUE_NAME = "USER_ID_QUEUE";
    @Getter
    private final static String HOST_NAME = "rabbitmq";

    private final static Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Operation(
            summary = "Fetches value of rabbitMQ queue to get current user id",
            description = "User-service sent current user data to queue. This method takes this value and passes it variables"
    )
    public static UUID receiveUserFromQueue(UserIdWrapper userIdWrapper) {
        try {
            logger.info("Rabbit MQ factory started");
            ConnectionFactory factory = new ConnectionFactory();
            logger.info("Host is set");
            factory.setHost(RabbitMQConfig.getHOST_NAME());
            logger.info("Connection is set");
            Connection connection = factory.newConnection();
            logger.info("Channel is set");
            Channel channel = connection.createChannel();
            logger.info("Queue Declaration");
            channel.queueDeclare(RabbitMQConfig.getQUEUE_NAME(), false, false, false, null);

            logger.info("Delivery Callback process started");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                logger.info("Receiving a message");
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                logger.info("Transforming message from string to uuid");
                userIdWrapper.setUserId(UUID.fromString(message));
                logger.info("Message with current userId: " + userIdWrapper.getUserId());
            };
            logger.info("Basic consume");
            channel.basicConsume(RabbitMQConfig.getQUEUE_NAME(), true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            logger.error("Failed to receive a message from Rabbit");
        }
        return userIdWrapper.getUserId();
    }

}
