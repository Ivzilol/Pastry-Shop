package com.example.pastry.shop.model.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChatMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String message;
    @Column
    private LocalDateTime createdDate;

    @Column
    private Long adminId;

    @Column(columnDefinition = "boolean default false")
    boolean isItAnswered;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users sendBy;

    public ChatMessages() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Users getSendBy() {
        return sendBy;
    }

    public void setSendBy(Users sendBy) {
        this.sendBy = sendBy;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public boolean isItAnswered() {
        return isItAnswered;
    }

    public void setItAnswered(boolean itAnswered) {
        isItAnswered = itAnswered;
    }
}
