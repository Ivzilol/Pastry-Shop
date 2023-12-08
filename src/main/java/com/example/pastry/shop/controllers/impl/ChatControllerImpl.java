package com.example.pastry.shop.controllers.impl;

import com.example.pastry.shop.model.dto.ChatMessageDTO;
import com.example.pastry.shop.model.dto.Message;
import com.example.pastry.shop.model.dto.UnansweredMessagesDTO;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.impl.MessageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.example.pastry.shop.common.ExceptionMessages.UNSUCCESSFUL_MAKE_CHAT_ENDED;

@RestController
@Tag(name = "Chat")
public class ChatControllerImpl {

    private final MessageServiceImpl messageService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatControllerImpl(MessageServiceImpl messageService, SimpMessagingTemplate simpMessagingTemplate) {
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

    @Operation(summary = "Get all user message", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get all user message",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UnansweredMessagesDTO.class)))}
    )
    @GetMapping("api/chat-room/admin/all")
    public ResponseEntity<?> getAllUsersMessage(@AuthenticationPrincipal Users user) {
        Set<UnansweredMessagesDTO> unansweredMessagesDTO = this.messageService.getAllUsersMessages(user);
        return ResponseEntity.ok(unansweredMessagesDTO);
    }

    @Operation(summary = "Get message from user")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get message from user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChatMessageDTO.class)))}
    )
    @GetMapping("api/chat-room/get-messages/{room}")
    public ResponseEntity<?> getMessagesFromUser(@PathVariable String room) {
        Set<ChatMessageDTO> chatMessageDTO = this.messageService.findMessagesByUser(room);
        return ResponseEntity.ok(chatMessageDTO);
    }

    @Operation(summary = "Admin make conversation finished", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "202", description = "Admin make conversation finished"),
                    @ApiResponse(description = "Invalid message",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class)))
            }
    )
    @PatchMapping("api/chat-room/admin/finish/{room}")
    public ResponseEntity<?> makeChatFinished(@AuthenticationPrincipal Users user,
                                              @PathVariable String room) {
        boolean isFinished = this.messageService.finishChat(user, room);
        if (isFinished) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom(UNSUCCESSFUL_MAKE_CHAT_ENDED);
            return ResponseEntity.ok(customResponse);
        }
    }
}
