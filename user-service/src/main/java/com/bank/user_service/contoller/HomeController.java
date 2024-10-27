package com.bank.user_service.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/home")
    private Map<String, Object> baseController() {
        HashMap<String, Object> currentUserData = new HashMap<String, Object>();
        currentUserData.put("name", UserAuthenticationController.currentUser.getName());
        currentUserData.put("surname", UserAuthenticationController.currentUser.getSurname());
        currentUserData.put("id", UserAuthenticationController.currentUser.getId());
        return currentUserData;
    }
}
