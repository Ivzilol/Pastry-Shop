package com.example.pastry.shop.controllers.impl;

import com.example.pastry.shop.controllers.CommentController;
import com.example.pastry.shop.model.dto.CommentAllDTO;
import com.example.pastry.shop.model.dto.CommentDto;
import com.example.pastry.shop.model.entity.Comment;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.impl.CommentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static com.example.pastry.shop.common.ConstantMessages.SUCCESSFUL_DELETE_MESSAGE;

@RestController
@Tag(name = "Comments")
public class CommentControllerImpl implements CommentController {

    private final CommentServiceImpl commentService;

    public CommentControllerImpl(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Create new comment", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Create new comment",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentAllDTO.class))})
            }
    )
    @Override
    public ResponseEntity<?> createComment(CommentDto commentDto, Users user) {
        CommentAllDTO commentsDTO = getCommentAllDTO(commentDto, user);
        return ResponseEntity.ok(commentsDTO);
    }

    @Operation(summary = "Edit comment", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Edit comment",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentAllDTO.class))})
            }
    )
    @Override
    public ResponseEntity<?> updateComment(CommentDto commentDto, Users user) {
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

    @Operation(summary = "Get all comments")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get all comments",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentAllDTO.class))})
            }
    )
    @Override
    public ResponseEntity<Set<?>> getCommentsByShop(Long shopId) {
        Set<CommentAllDTO> comments = commentService.getCommentsByShopId(shopId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Admin delete user comments")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Admin delete user comments"),
                    @ApiResponse(responseCode = "500", description = "Unsuccessful delete comment")
            }
    )
    @Override
    public ResponseEntity<?> deleteComment(Long id) {
        try {
            commentService.deleteComment(id);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom(SUCCESSFUL_DELETE_MESSAGE);
            return ResponseEntity.ok(customResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Admin delete user comment")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Admin delete user comment",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)))
            }
    )
    @Override
    public ResponseEntity<?> deleteUserComment(Long id) {
        commentService.deleteComment(id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom(SUCCESSFUL_DELETE_MESSAGE);
        return ResponseEntity.ok().body(customResponse);
    }
}
