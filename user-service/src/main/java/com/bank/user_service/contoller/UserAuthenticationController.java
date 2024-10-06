package com.bank.user_service.contoller;

import com.bank.user_service.model.User;
import com.bank.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserAuthenticationController {

    @Autowired
    private UserRepository userRepository;

    // Authentication endpoint
    @PostMapping("auth/{email}/{password}")
    public ResponseEntity<?> authentication(@PathVariable String email, @PathVariable String password) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                return ResponseEntity.ok("Authentication successful.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
}