package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.Message;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    private final MessageService messageService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(MessageService messageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/message/{room}")
    public synchronized void receiveMessage(@Payload Message message, @DestinationVariable String room) {
        simpMessagingTemplate.convertAndSend("/chat-rooms/" + room, message);
//        if (message.getMessage() != null) {
//            messageService.saveMessage(message, room);
//        }
    }

    @PatchMapping("/admin/finish/{id}")
    public ResponseEntity<?> makeChatFinished(@AuthenticationPrincipal Users user,
                                              @PathVariable Long id) {
        boolean isFinished = this.messageService.finishChat(user, id);
        if (isFinished) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom("Invalid message");
            return ResponseEntity.ok(customResponse);
        }
    }
}
