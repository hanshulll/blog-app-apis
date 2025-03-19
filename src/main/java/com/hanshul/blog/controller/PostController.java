package com.hanshul.blog.controller;

import com.hanshul.blog.dto.PostDto;
import com.hanshul.blog.service.PostService;
import com.hanshul.blog.utility.BlogAppResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {
    /////////////////////////////////////////////////
    ///// VARIABLES
    /////////////////////////////////////////////////

    private PostService postService;

    /////////////////////////////////////////////////
    /////// CONSTRUCTOR
    /////////////////////////////////////////////////

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /////////////////////////////////////////////////
    /////// METHODS
    /////////////////////////////////////////////////

    @PostMapping("/users/{userId}/categories/{categoryId}/posts")
    public ResponseEntity<BlogAppResponse> createPost(@PathVariable("userId") Integer userId,
            @PathVariable("categoryId") Integer categoryId, @Valid @RequestBody PostDto requestBody) {
        return this.postService.createPost(requestBody, userId, categoryId);
    }

}
