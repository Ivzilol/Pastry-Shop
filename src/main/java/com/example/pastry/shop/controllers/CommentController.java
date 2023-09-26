package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.CommentDto;
import com.example.pastry.shop.model.dto.CommentsDTO;
import com.example.pastry.shop.model.entity.Comment;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto,
                                           @AuthenticationPrincipal Users user) {
        Comment comment = commentService.save(commentDto, user);
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setId(comment.getId());
        commentsDTO.setText(comment.getText());
        commentsDTO.setCreatedBy(comment.getCreatedBy());
        return ResponseEntity.ok(commentsDTO);
    }

    @PutMapping("{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto,
                                           @AuthenticationPrincipal Users user) {
        Comment comment = commentService.save(commentDto, user);
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setId(comment.getId());
        commentsDTO.setText(comment.getText());
        commentsDTO.setCreatedBy(comment.getCreatedBy());
        return ResponseEntity.ok(commentsDTO);
    }

    @GetMapping("")
    public ResponseEntity<Set<?>> getCommentsByShop(@RequestParam Long shopId) {
        Set<Comment> comments = commentService.getCommentsByShopId(shopId);
        Set<Object> response = new HashSet<>();
        for (Comment current : comments) {
            CommentsDTO commentsDTO = new CommentsDTO();
            commentsDTO.setId(current.getId());
            commentsDTO.setText(current.getText());
            commentsDTO.setCreatedBy(current.getCreatedBy());
            response.add(commentsDTO);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return (ResponseEntity<?>) ResponseEntity.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }
}
