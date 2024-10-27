package com.bank.user_service.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDateTime registeredDate;

    public User(String name, String surname, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.registeredDate = LocalDateTime.now();
    }
    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}