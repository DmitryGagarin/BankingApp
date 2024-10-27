package com.bank.deposit_service.repository;

import com.bank.deposit_service.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepositRepository extends JpaRepository<Deposit, UUID> {
}
