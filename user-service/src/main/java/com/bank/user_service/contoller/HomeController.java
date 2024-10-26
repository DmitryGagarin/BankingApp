package com.bank.user_service.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @GetMapping("/home")
    private String baseController() {
        return "Your in home now";
    }
}
