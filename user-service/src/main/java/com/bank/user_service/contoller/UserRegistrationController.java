package com.bank.user_service.contoller;

import com.bank.user_service.model.User;
import com.bank.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserRegistrationController {

    // подсунуть мок-объекты для тестирования
// кафку можно
// цепочка ответсвенности для редиса
    @Autowired
    public UserRepository userRepository;

    @PostMapping("/register/{firstName}/{lastName}/{email}/{password}")
    public ResponseEntity<?> registerUser(@PathVariable String firstName,
                                          @PathVariable String lastName,
                                          @PathVariable String email,
                                          @PathVariable String password) {

        Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
        logger.info("UserRegistrationController started");
        try {
            logger.info("UserRegistrationController attempt to create new user");
            User user = new User(firstName, lastName, email, password);
            try {
                logger.info("UserRegistrationController attempt to save new user");
                userRepository.save(user);
            } catch (Exception e) {
                logger.error("UserRegistrationController attempt to save new user failed");
            }
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            logger.error("UserRegistrationController attempt to create new user failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        } finally {
            logger.info("UserRegistrationController terminated");
        }
    }
}
