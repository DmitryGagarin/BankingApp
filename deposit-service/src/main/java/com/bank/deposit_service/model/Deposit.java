package com.bank.deposit_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;


@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "deposit")
public class Deposit {

    public enum DepositType {
        DEBIT_CARD,
        CREDIT_CARD,
        DEPOSIT
    }

    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private DepositType type;
    private String number;
    private String cvv;
    private BigDecimal amount;
    private LocalDateTime createDate;
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    private String CardNumberGenerator() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    private String CVVGenerator() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    public Deposit(UUID userId, DepositType type) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.number = CardNumberGenerator();
        this.cvv = CVVGenerator();
        this.amount = null;
        this.userId = userId;
        this.createDate = LocalDateTime.now();
    }

    public Deposit(UUID userId) {
        this.id = UUID.randomUUID();
        this.type = DepositType.DEBIT_CARD;
        this.number = CardNumberGenerator();
        this.cvv = CVVGenerator();
        this.amount = BigDecimal.valueOf(0);
        this.userId = userId;
        this.createDate = LocalDateTime.now();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Deposit deposit = (Deposit) o;
        return getId() != null && Objects.equals(getId(), deposit.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
