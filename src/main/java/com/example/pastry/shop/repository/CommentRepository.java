package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {

    @Query("select c from Comment as c " +
            "where c.shops.id = :shopId")
    Set<Comment> findByShopId(Long shopId);

}
