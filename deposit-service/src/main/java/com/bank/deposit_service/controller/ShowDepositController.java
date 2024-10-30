package com.bank.deposit_service.controller;

import com.bank.deposit_service.model.Deposit;
import com.bank.deposit_service.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin("/localhost:3000")
public class ShowDepositController {

    @Autowired
    private DepositRepository depositRepository;

    @GetMapping("/all_deposits")
    public List<Deposit> getAllDeposits() {
        return depositRepository.findByUserId(CreateDepositController.currentUserId);
    }
}
