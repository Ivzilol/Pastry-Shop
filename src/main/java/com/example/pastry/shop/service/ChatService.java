package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.GetMessageByUserDTO;
import com.example.pastry.shop.model.dto.GetUserMessagesDTO;
import com.example.pastry.shop.model.dto.SentMessageDto;
import com.example.pastry.shop.model.dto.UnansweredMessagesDTO;
import com.example.pastry.shop.model.entity.ChatMessages;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.ChatMessagesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class ChatService {

    private final ChatMessagesRepository chatMessagesRepository;

    public ChatService(ChatMessagesRepository chatMessagesRepository) {
        this.chatMessagesRepository = chatMessagesRepository;
    }

    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }

    private static boolean isUser(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.user.name().equals(auth.getAuthority()));
    }


    public Set<GetUserMessagesDTO> findByUserId(Users users) {
        return this.chatMessagesRepository.findBySendBy(users.getId());
    }

    public boolean saveMessage(SentMessageDto sentMessageDto, Users user) {
        if (!sentMessageDto.getNewMessage().trim().isEmpty() && user.isValidate() && isUser(user)) {
            ChatMessages chatMessages = new ChatMessages();
            chatMessages.setMessage(sentMessageDto.getNewMessage());
            chatMessages.setCreatedDate(LocalDateTime.now());
            chatMessages.setSendBy(user);
            this.chatMessagesRepository.save(chatMessages);
            return true;
        } else if (!sentMessageDto.getNewMessage().trim().isEmpty() && user.isValidate() && isAdmin(user)) {
            return true;
        } else {
            return false;
        }
    }

    public Set<UnansweredMessagesDTO> findUnansweredMessages(Users user) {
        return this.chatMessagesRepository.findAllUnansweredMessages();
    }

    public Set<GetMessageByUserDTO> findMessagesByUserId(Long id, Users user) {
        return null;
    }
}
