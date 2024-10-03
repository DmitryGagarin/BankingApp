package com.bank.user_service.contoller;

import com.bank.user_service.model.User;
import com.bank.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserRegistrationController {

    @Autowired
    public UserRepository userRepository;

    @PostMapping("/register/{name}/{surname}/{email}/{password}")
    private User registration(@PathVariable String name,
                              @PathVariable String surname,
                              @PathVariable String email,
                              @PathVariable String password) {

        User user = new User(name, surname, email, password);
        return userRepository.save(user);
    }
}
