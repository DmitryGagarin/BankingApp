package com.bank.user_service.contoller;

import com.bank.user_service.model.User;
import com.bank.user_service.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserAuthenticationController", description = "Allows user to login")
@RestController
@CrossOrigin("http://localhost:3000")
public class UserAuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("login/{email}/{password}")
    public ResponseEntity<?> authentication(@PathVariable String email, @PathVariable String password) {

        Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);
        logger.info("UserAuthenticationController started");

        try {
            logger.info("UserAuthenticationController attempt to find a user");
            User user = userRepository.findByEmail(email);
            try {
                logger.info("UserAuthenticationController attempt to check password started");
                if (user != null) {
                    if (user.getPassword().equals(password)) {
                        logger.info("UserAuthenticationController Authentication successful");
                        return ResponseEntity.ok("User authenticated successfully.");
                    } else {
                        logger.warn("UserAuthenticationController Authentication failed");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
                    }
                }
                logger.info("UserAuthenticationController attempt to check password ended");
            } catch (Exception e) {
                logger.error("UserAuthenticationController attempt to find user failed");
            }
        } catch (Exception e) {
            logger.error("UserAuthenticationController failed");
        }
        logger.info("UserAuthenticationController ended");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
}