package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.CommentAllDTO;
import com.example.pastry.shop.model.dto.CommentDto;
import com.example.pastry.shop.model.entity.Comment;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.CommentRepository;
import com.example.pastry.shop.repository.ShopsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ShopsRepository shopsRepository;
    private final ModelMapper mapper;

    public CommentService(CommentRepository commentRepository, ShopsRepository shopsRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.shopsRepository = shopsRepository;
        this.mapper = mapper;
    }

    public Comment save(CommentDto commentDto, Users user) {
        Optional<Shops> shopId = shopsRepository.findById(commentDto.getShopId());
        Comment comment = mapper.map(commentDto, Comment.class);
        comment.setCreatedBy(user);
        if (comment.getId() == null) {
            comment.setCreatedDate(LocalDateTime.now());
        }
        comment.setShops(shopId.get());
        return commentRepository.save(comment);
    }

    public Set<CommentAllDTO> getCommentsByShopId(Long shopId) {
        return commentRepository.findByShopId(shopId);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
