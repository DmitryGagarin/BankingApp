package com.bank.user_service.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/home")
    private Map<String, String> baseController() {
        HashMap<String, String> currentUserData = new HashMap<String, String>();
        currentUserData.put("name", UserAuthenticationController.currentUser.getName());
        currentUserData.put("surname", UserAuthenticationController.currentUser.getSurname());
        return currentUserData;
    }
}
