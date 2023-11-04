package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.CommentAllDTO;
import com.example.pastry.shop.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {

    @Query("select new com.example.pastry.shop.model.dto.CommentAllDTO(" +
            "u.username, c.id, c.text)" +
            " from Comment as c" +
            " join Users as u on c.createdBy.id = u.id" +
            " where c.shops.id = :shopId")
    Set<CommentAllDTO> findByShopId(Long shopId);

}
