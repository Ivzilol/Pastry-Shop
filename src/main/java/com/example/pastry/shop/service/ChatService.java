package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.ChatMessages;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.ChatMessagesRepository;
import com.example.pastry.shop.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class ChatService {

    private final ChatMessagesRepository chatMessagesRepository;

    private final UsersRepository usersRepository;

    public ChatService(ChatMessagesRepository chatMessagesRepository, UsersRepository usersRepository) {
        this.chatMessagesRepository = chatMessagesRepository;
        this.usersRepository = usersRepository;
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
        return this.chatMessagesRepository.findMessagesByUserId(id);
    }

    public boolean saveMessageAdmin(SendMessageAdminDTO sendMessageAdminDTO, Long id, Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            ChatMessages chatMessages = new ChatMessages();
            chatMessages.setAdminId(user.getId());
            chatMessages.setMessage(sendMessageAdminDTO.getNewMessageAdmin());
            chatMessages.setCreatedDate(LocalDateTime.now());
            Optional<Users> userAnsweredId = usersRepository.findById(id);
            chatMessages.setSendBy(userAnsweredId.get());
            this.chatMessagesRepository.save(chatMessages);
        }
        return true;
    }
}
