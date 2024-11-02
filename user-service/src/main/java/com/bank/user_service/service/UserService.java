package com.bank.user_service.service;

import com.bank.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    public boolean checkIfEmailExists(String email) {
//        return userRepository.existsByEmail(email);
//    }
}
