package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.ChatMessageDTO;
import com.example.pastry.shop.model.dto.UnansweredMessagesDTO;
import com.example.pastry.shop.model.entity.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessages, Long> {


    @Query("select new com.example.pastry.shop.model.dto.UnansweredMessagesDTO(" +
            " m.message as message, m.sendBy.id as userId, m.sendBy.username)" +
            " from ChatMessages as m" +
            " where m.isItAnswered = false" +
            " group by userId")
    Set<UnansweredMessagesDTO> findAllUnansweredMessages();

    @Query("select m from ChatMessages as m" +
            " where m.isItAnswered = true")
    Set<ChatMessages> deleteFinishedChat();

    @Query("select new com.example.pastry.shop.model.dto.ChatMessageDTO(" +
            "m.id, m.message, m.createdDate, u.username, m.adminId)" +
            " from ChatMessages as m" +
            " join Users as u on m.sendBy.id = u.id" +
            " where u.username = :username")
    Set<ChatMessageDTO> findMessagesByUser(String username);

    @Query("select m from ChatMessages as m" +
            " join Users as u on m.sendBy.id = u.id" +
            " where u.username = :userUsername" +
            " and m.isItAnswered = false")
    Set<ChatMessages> findAllMessagesById(String userUsername);
}
