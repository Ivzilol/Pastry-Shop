package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.ChatMessageDTO;
import com.example.pastry.shop.model.dto.Message;
import com.example.pastry.shop.model.dto.UnansweredMessagesDTO;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface MessageService {

    boolean finishChat(Users user, String userUsername);

    void saveMessage(Message message, String room);

    Set<UnansweredMessagesDTO> getAllUsersMessages(Users user);

    Set<ChatMessageDTO> findMessagesByUser(String username);
}


