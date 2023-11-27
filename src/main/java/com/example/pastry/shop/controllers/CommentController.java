package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.CommentDto;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api/comments")
public interface CommentController {

    @PostMapping("")
    ResponseEntity<?> createComment(@RequestBody CommentDto commentDto,
                                    @AuthenticationPrincipal Users user);
    @PutMapping("{commentId}")
    ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto,
                                    @AuthenticationPrincipal Users user);
    @GetMapping("")
    ResponseEntity<Set<?>> getCommentsByShop(@RequestParam Long shopId);

    @DeleteMapping("{id}")
    ResponseEntity<?> deleteComment(@PathVariable Long id);

    @DeleteMapping("/user/{id}")
    ResponseEntity<?> deleteUserComment(@PathVariable Long id);

}
