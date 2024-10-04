package com.bank.user_service.contoller;

import com.bank.user_service.model.User;
import com.bank.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserRegistrationController {

    @Autowired
    public UserRepository userRepository;

    @PostMapping("/register/{firstName}/{lastName}/{email}/{password}")
    public ResponseEntity<?> registerUser(@PathVariable String firstName,
                                          @PathVariable String lastName,
                                          @PathVariable String email,
                                          @PathVariable String password) {
        // Example of user creation logic
        try {
            // Validate input, check if user already exists, etc.
            User user = new User(firstName, lastName, email, password);
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }
}
