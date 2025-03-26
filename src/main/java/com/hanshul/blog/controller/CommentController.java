package com.hanshul.blog.controller;

import com.hanshul.blog.payloads.PublishCommentRequestPayload;
import com.hanshul.blog.service.CommentService;
import com.hanshul.blog.utility.BlogAppResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Comment Controller", description = "API under this section handles requests related to comments on posts")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/post/comments")
    @Operation(summary = "API to add a comment on a post", description = "This api adds a comment on a post")
    public ResponseEntity<BlogAppResponse> addComment(@RequestBody PublishCommentRequestPayload requestPayload) {
        return this.commentService.createComment(requestPayload);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/user/{userId}/post/{postId}/comment/{commentId}")
    @Operation(summary = "API to delete comment", description = "This api deletes a comment from a post")
    public ResponseEntity<BlogAppResponse> deleteComment(@PathVariable("userId") int userId,
            @PathVariable("postId") int postId, @PathVariable("commentId") int commentId) {
        return this.commentService.deleteComment(postId, userId, commentId);
    }

}
