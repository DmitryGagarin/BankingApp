package com.bank.user_service.contoller;

import com.bank.user_service.model.User;
import com.bank.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserAuthenticationController {

    @Autowired
    public UserRepository userRepository;

    @PostMapping("auth/{email}/{password")
    private boolean authentication(@PathVariable String email, @PathVariable String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }
}
