package com.example.pastry.shop.model.entity;

import com.example.pastry.shop.model.enums.AuthorityEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityEnum userRole;

    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityEnum getUserRole() {
        return userRole;
    }

    public void setUserRole(AuthorityEnum userRole) {
        this.userRole = userRole;
    }
}
