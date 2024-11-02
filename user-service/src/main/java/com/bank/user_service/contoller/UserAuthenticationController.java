package com.bank.user_service.contoller;

import com.bank.user_service.model.User;
import com.bank.user_service.repository.UserRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Tag(name = "UserAuthenticationController", description = "Allows user to login")
@RestController
@CrossOrigin("http://localhost:3000")
public class UserAuthenticationController {

    @Autowired
    private UserRepository userRepository;

    private final static String QUEUE_NAME = "USER_ID_QUEUE";
    private final static String HOST_NAME = "rabbitmq";

    protected static User currentUser = null;

    @PostMapping("login/{email}/{password}")
    public ResponseEntity<?> authentication(@PathVariable String email, @PathVariable String password) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);

        Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);
        logger.info("UserAuthenticationController started");

        try {
            logger.info("UserAuthenticationController attempt to find a user");
            User user = userRepository.findByEmail(email);
            currentUser = user;

            if (user == null) {
                logger.warn("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            logger.info("Started RabbitMQ connection");
            try (Connection connection = factory.newConnection()) {
                logger.info("Creating channel");
                Channel channel = connection.createChannel();
                logger.info("Queue Declaring started");
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                logger.info("Generating a message");

                String message = String.valueOf(currentUser.getId());
                logger.info("Basic Publish");
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                logger.info("UUID of current user was sent: " + message);
            } catch (Exception e) {
                logger.error("Failed to send a message via RabbitMQ", e);
            }

            logger.info("UserAuthenticationController attempt to check password started");
            if (user.getPassword().equals(password)) {
                logger.info("UserAuthenticationController Authentication successful");
                return ResponseEntity.ok("User authenticated successfully.");
            } else {
                logger.warn("UserAuthenticationController Authentication failed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }

        } catch (Exception e) {
            logger.error("UserAuthenticationController failed", e);
        } finally {
            logger.info("UserAuthenticationController ended");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
    }
}