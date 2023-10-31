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
public class MessageService {

    private final ChatMessagesRepository chatMessagesRepository;

    private final UsersRepository usersRepository;

    public MessageService(ChatMessagesRepository chatMessagesRepository, UsersRepository usersRepository) {
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


    public boolean finishChat(Users user, String userUsername) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            Set<ChatMessages> chatMessages = this.chatMessagesRepository.findAllMessagesById(userUsername);
            for (ChatMessages current : chatMessages) {
                current.setItAnswered(true);
                this.chatMessagesRepository.save(current);
            }
        }
        return true;
    }

    public void saveMessage(Message message, String room) {
        Optional<Users> user = this.usersRepository.findByUsername(message.getSenderName());
        ChatMessages chatMessages = new ChatMessages();
        if (isUser(user.get())) {
            chatMessages.setMessage(message.getMessage());
            chatMessages.setCreatedDate(LocalDateTime.now());
            chatMessages.setSendBy(user.get());
        } else {
            chatMessages.setMessage(message.getMessage());
            chatMessages.setCreatedDate(LocalDateTime.now());
            Optional<Users> recipient = this.usersRepository.findByUsername(room);
            chatMessages.setSendBy(recipient.get());
            chatMessages.setAdminId(user.get().getId());
        }
        this.chatMessagesRepository.save(chatMessages);
    }

    public Set<UnansweredMessagesDTO> getAllUsersMessages(Users user) {
        return this.chatMessagesRepository.findAllUnansweredMessages();

    }

    public Set<ChatMessageDTO> findMessagesByUser(String username) {
        return this.chatMessagesRepository.findMessagesByUser(username);
    }
}
