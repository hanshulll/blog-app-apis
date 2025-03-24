package com.hanshul.blog.service;

import com.hanshul.blog.payloads.PublishCommentRequestPayload;
import com.hanshul.blog.utility.BlogAppResponse;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<BlogAppResponse> createComment(PublishCommentRequestPayload requestPayload);
    ResponseEntity<BlogAppResponse> deleteComment(int postId, int userId, int commentId);
}
