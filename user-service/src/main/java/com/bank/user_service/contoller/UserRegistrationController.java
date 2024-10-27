package com.bank.user_service.contoller;

import com.bank.user_service.model.User;
import com.bank.user_service.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "UserRegistrationController", description = "Allows user to register")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserRegistrationController {

    // подсунуть мок-объекты для тестирования
    // кафку можно
    // цепочка ответсвенности для редиса
    @Autowired
    public UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    @Operation(
            summary = "Registers user",
            description = "Create post-mapping url that's filled from front"
    )
    @PostMapping("/register/{firstName}/{lastName}/{email}/{password}")
    public ResponseEntity<String> registerUser(@PathVariable String firstName,
                                               @PathVariable String lastName,
                                               @PathVariable String email,
                                               @PathVariable String password) {

        logger.info("User registration process started for {}", email);

//         Check if the email already exists
//        if (userService.checkIfEmailExists(email)) {
//            logger.warn("Registration attempt failed: email {} already exists", email);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Email already exists.");
//        }

        // Create and save the new user
        User user = new User(firstName, lastName, email, password);
        try {
            logger.info("Attempting to save new user: {}", email);
            userRepository.save(user);
            logger.info("User registered successfully: {}", email);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (Exception e) {
            logger.error("User registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }
}
