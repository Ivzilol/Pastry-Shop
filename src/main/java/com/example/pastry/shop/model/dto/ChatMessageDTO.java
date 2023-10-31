package com.example.pastry.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatMessageDTO {

    private Long id;

    private String message;

    private LocalDateTime createdDate;

    private String username;

    private Long adminId;
}
