package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.CommentAllDTO;
import com.example.pastry.shop.model.dto.CommentDto;
import com.example.pastry.shop.model.entity.Comment;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface CommentService {


    Comment save(CommentDto commentDto, Users user);

    Set<CommentAllDTO> getCommentsByShopId(Long shopId);

    void deleteComment(Long id);
}
