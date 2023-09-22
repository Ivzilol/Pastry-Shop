package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.SentMessageDto;
import com.example.pastry.shop.model.entity.ChatMessages;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/chatroom")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("")
    public ResponseEntity<?> getUserMessages(@AuthenticationPrincipal Users users) {
        Set<ChatMessages> findChatMessage = this.chatService.findByUserId(users);
        return ResponseEntity.ok(findChatMessage);
    }

    @PostMapping("/send")
    public ResponseEntity<?> sentMessage(@AuthenticationPrincipal Users user,
                                         @RequestBody SentMessageDto sentMessageDto) {
        boolean isSave =  this.chatService.saveMessage(sentMessageDto, user);
        if (isSave) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom("Invalid message");
            return ResponseEntity.ok(customResponse);
        }
    }
}
