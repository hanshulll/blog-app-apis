package com.hanshul.blog.service.impl;

import com.hanshul.blog.entities.CommentEntity;
import com.hanshul.blog.entities.PostEntity;
import com.hanshul.blog.entities.UserEntity;
import com.hanshul.blog.exceptions.ResourceNotFoundException;
import com.hanshul.blog.payloads.PublishCommentRequestPayload;
import com.hanshul.blog.repositories.CommentRepository;
import com.hanshul.blog.repositories.PostRepository;
import com.hanshul.blog.repositories.UserRepository;
import com.hanshul.blog.service.CommentService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepo;
    private CommentRepository commentRepo;
    private ModelMapper modelMapper;
    private UserRepository userRepo;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo, ModelMapper modelMapper,
            UserRepository userRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
    }

    @Override
    public ResponseEntity<BlogAppResponse> deleteComment(int postId, int userId, int commentId) {
        Instant startTime = Instant.now();
        Optional<CommentEntity> comment = this.commentRepo.findById(commentId);
        comment.ifPresent(postComment -> {
            if (Objects.equals(userId, postComment.getUser().getId())
                    && Objects.equals(postId, postComment.getPost().getPostId())) {
                this.commentRepo.delete(postComment);
            }
        });
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build())
                .data(Map.of("status",
                        String.format("Request to delete comment %s on postId %s, by user %s is received", commentId,
                                postId, userId)))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BlogAppResponse> createComment(PublishCommentRequestPayload requestPayload) {
        Instant startTime = Instant.now();
        UserEntity user = this.userRepo.findById(requestPayload.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", requestPayload.getUserId()));
        PostEntity post = this.postRepo.findById(requestPayload.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", requestPayload.getPostId()));
        CommentEntity comment = CommentEntity.builder().content(requestPayload.getContent()).post(post).user(user)
                .build();
        CommentEntity savedComment = this.commentRepo.save(comment);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.CREATED.value()).request(requestPayload).build())
                .data(Map.of("message",
                        String.format("Comment has been added successfully on post_id : %s with comment_id",
                                post.getPostId(), savedComment.getId())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
