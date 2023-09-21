package com.example.pastry.shop.model.entity;

import com.example.pastry.shop.model.enums.StatusMessage;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChatMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String senderName;

    @Column
    private String receiverName;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column
    private LocalDateTime createdDate;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusMessage status;

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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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

    public StatusMessage getStatus() {
        return status;
    }

    public void setStatus(StatusMessage status) {
        this.status = status;
    }

    public Users getSendBy() {
        return sendBy;
    }

    public void setSendBy(Users sendBy) {
        this.sendBy = sendBy;
    }
}
