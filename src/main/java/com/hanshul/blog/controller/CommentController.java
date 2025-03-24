package com.hanshul.blog.controller;

import com.hanshul.blog.payloads.PublishCommentRequestPayload;
import com.hanshul.blog.service.CommentService;
import com.hanshul.blog.utility.BlogAppResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/comments")
    public ResponseEntity<BlogAppResponse> addComment(@RequestBody PublishCommentRequestPayload requestPayload) {
        return this.commentService.createComment(requestPayload);
    }

    @DeleteMapping("/user/{userId}/post/{postId}/comment/{commentId}")
    public ResponseEntity<BlogAppResponse> deleteComment(@PathVariable("userId") int userId,
            @PathVariable("postId") int postId, @PathVariable("commentId") int commentId) {
        return this.commentService.deleteComment(postId, userId, commentId);
    }

}
