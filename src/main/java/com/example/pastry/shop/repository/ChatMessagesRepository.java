package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.ChatMessages;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessages, Long> {


    Set<ChatMessages> findBySendBy(Users sendBy);
}
