package com.bank.user_service.contoller;

import com.bank.user_service.config.RabbitMQConfig;
import com.bank.user_service.model.User;
import com.bank.user_service.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserAuthenticationController", description = "Allows user to login")
@RestController
@CrossOrigin("http://localhost:3000")
public class UserAuthenticationController {

    @Autowired
    private UserRepository userRepository;

//    private final static String QUEUE_NAME = "USER_ID_QUEUE";
//    private final static String HOST_NAME = "rabbitmq";
    private final static Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);
    protected static User currentUser = null;

//    private ResponseEntity<?> sendCurrentUserToQueue(User currentUser) {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost(RabbitMQConfig.HOST_NAME);
//
//        try (Connection connection = factory.newConnection()) {
//            logger.info("Creating channel");
//            Channel channel = connection.createChannel();
//            logger.info("Queue Declaring started");
//            channel.queueDeclare(RabbitMQConfig.QUEUE_NAME, false, false, false, null);
//            logger.info("Generating a message");
//
//            String message = String.valueOf(currentUser.getId());
//            logger.info("Basic Publish");
//            channel.basicPublish("", RabbitMQConfig.QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
//            logger.info("UUID of current user was sent: " + message);
//        } catch (Exception e) {
//            logger.error("Failed to send a message via RabbitMQ", e);
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body("Current user id was sent");
//    }

    @PostMapping("login/{email}/{password}")
    public ResponseEntity<?> authentication(@PathVariable String email, @PathVariable String password) {

//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost(HOST_NAME);

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
            RabbitMQConfig.sendCurrentUserToQueue(currentUser);
//            try (Connection connection = factory.newConnection()) {
//                logger.info("Creating channel");
//                Channel channel = connection.createChannel();
//                logger.info("Queue Declaring started");
//                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//                logger.info("Generating a message");
//
//                String message = String.valueOf(currentUser.getId());
//                logger.info("Basic Publish");
//                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
//                logger.info("UUID of current user was sent: " + message);
//            } catch (Exception e) {
//                logger.error("Failed to send a message via RabbitMQ", e);
//            }

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