package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.GetMessageByUserDTO;
import com.example.pastry.shop.model.dto.GetUserMessagesDTO;
import com.example.pastry.shop.model.dto.UnansweredMessagesDTO;
import com.example.pastry.shop.model.entity.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessages, Long> {

    @Query("select new com.example.pastry.shop.model.dto.GetUserMessagesDTO(" +
            " m.id ,m.message as message, m.adminId as adminId)" +
            " from ChatMessages as m" +
            " where m.sendBy.id = :id")
    Set<GetUserMessagesDTO> findBySendBy(Long id);


    @Query("select new com.example.pastry.shop.model.dto.UnansweredMessagesDTO(" +
            " m.message as message, m.sendBy.id as userId)" +
            " from ChatMessages as m" +
            " where m.isItAnswered = false" +
            " group by userId")
    Set<UnansweredMessagesDTO> findAllUnansweredMessages();

    @Query("select new com.example.pastry.shop.model.dto.GetMessageByUserDTO(" +
            " m.message as message, m.sendBy.id as userId, m.adminId)" +
            " from ChatMessages  as m" +
            " where m.isItAnswered = false" +
            " and m.sendBy.id = :id")
    Set<GetMessageByUserDTO> findMessagesByUserId(Long id);

    @Query("select m from ChatMessages as m" +
            " where m.sendBy.id = :id" +
            " and m.isItAnswered = false")
    Set<ChatMessages> findAllMessagesById(Long id);

    @Query("delete from ChatMessages " +
            " where isItAnswered = true")
    void deleteFinishedChat();
}
