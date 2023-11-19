package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.CommentAllDTO;
import com.example.pastry.shop.model.dto.CommentDto;
import com.example.pastry.shop.model.entity.Comment;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
@Tag(name = "Comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto,
                                           @AuthenticationPrincipal Users user) {
        CommentAllDTO commentsDTO = getCommentAllDTO(commentDto, user);
        return ResponseEntity.ok(commentsDTO);
    }


    @PutMapping("{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto,
                                           @AuthenticationPrincipal Users user) {
        CommentAllDTO commentsDTO = getCommentAllDTO(commentDto, user);
        return ResponseEntity.ok(commentsDTO);
    }

    @NotNull
    private CommentAllDTO getCommentAllDTO(CommentDto commentDto, Users user) {
        Comment comment = commentService.save(commentDto, user);
        CommentAllDTO commentsDTO = new CommentAllDTO();
        commentsDTO.setId(comment.getId());
        commentsDTO.setText(comment.getText());
        commentsDTO.setCreatedBy(comment.getCreatedBy().getUsername());
        return commentsDTO;
    }

    @GetMapping("")
    public ResponseEntity<Set<?>> getCommentsByShop(@RequestParam Long shopId) {
        Set<CommentAllDTO> comments = commentService.getCommentsByShopId(shopId);
        return ResponseEntity.ok(comments);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom("Successful delete message");
        return ResponseEntity.ok().body(customResponse);

    }
}
