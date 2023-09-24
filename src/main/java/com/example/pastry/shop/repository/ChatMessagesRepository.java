package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.GetUserMessagesDTO;
import com.example.pastry.shop.model.entity.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessages, Long> {

    @Query("select new com.example.pastry.shop.model.dto.GetUserMessagesDTO(" +
            " m.message as message)" +
            " from ChatMessages as m" +
            " where m.sendBy.id = :id")
    Set<GetUserMessagesDTO> findBySendBy(Long id);
}
