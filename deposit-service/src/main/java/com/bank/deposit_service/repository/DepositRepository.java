package com.bank.deposit_service.repository;

import com.bank.deposit_service.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DepositRepository extends JpaRepository<Deposit, UUID> {

    List<Deposit> findByUserId(UUID id);

}
