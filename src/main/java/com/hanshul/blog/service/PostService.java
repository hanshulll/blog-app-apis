package com.hanshul.blog.service;

import com.hanshul.blog.dto.PostDto;
import com.hanshul.blog.payloads.CreatePostRequestModel;
import com.hanshul.blog.utility.BlogAppResponse;
import org.springframework.http.ResponseEntity;

public interface PostService {
    ResponseEntity<BlogAppResponse> createPost(CreatePostRequestModel requestBody, Integer userId, Integer categoryId);
    ResponseEntity<BlogAppResponse> updatePost(PostDto createPostRequestBody, Integer postId);
    ResponseEntity<BlogAppResponse> deletePost(Integer postId);
    ResponseEntity<BlogAppResponse> getAllPost();
    ResponseEntity<BlogAppResponse> getPostById(Integer postId);
    ResponseEntity<BlogAppResponse> getPostByCategory(Integer categoryId);
    ResponseEntity<BlogAppResponse> getPostByUser(Integer userId);
    ResponseEntity<BlogAppResponse> searchPostByKeyword(String keyword);
}
