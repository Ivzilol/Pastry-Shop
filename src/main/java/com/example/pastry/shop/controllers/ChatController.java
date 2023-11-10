package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.ChatMessageDTO;
import com.example.pastry.shop.model.dto.Message;
import com.example.pastry.shop.model.dto.UnansweredMessagesDTO;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.MessageService;
import com.example.pastry.shop.util.MessageInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net"}, allowCredentials = "true", allowedHeaders = "true")
public class ChatController {

    private final MessageService messageService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(MessageService messageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/message/{room}")
    public void receiveMessage(@Payload Message message, @DestinationVariable String room) {
        simpMessagingTemplate.convertAndSend("/chat-rooms/" + room, message);
        synchronized (this) {
            if (message.getMessage() != null) {
                messageService.saveMessage(message, room);
            }
        }
    }

    @GetMapping("api/chat-room/admin/all")
    public ResponseEntity<?> getAllUsersMessage(@AuthenticationPrincipal Users user) {
        Set<UnansweredMessagesDTO> unansweredMessagesDTO = this.messageService.getAllUsersMessages(user);
        return ResponseEntity.ok(unansweredMessagesDTO);
    }

    @GetMapping("api/chat-room/get-messages/{room}")
    public ResponseEntity<?> getMessagesFromUser(@PathVariable String room) {
        Set<ChatMessageDTO> chatMessageDTO = this.messageService.findMessagesByUser(room);
        return ResponseEntity.ok(chatMessageDTO);
    }

    @PatchMapping("api/chat-room/admin/finish/{room}")
    public ResponseEntity<?> makeChatFinished(@AuthenticationPrincipal Users user,
                                              @PathVariable String room) {
        boolean isFinished = this.messageService.finishChat(user, room);
        if (isFinished) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom("Invalid message");
            return ResponseEntity.ok(customResponse);
        }
    }
}
