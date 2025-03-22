package com.hanshul.blog.controller;

import com.hanshul.blog.payloads.CreatePostRequestModel;
import com.hanshul.blog.service.PostService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.Constant;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @PathVariable("categoryId") Integer categoryId, @Valid @RequestBody CreatePostRequestModel requestBody) {
        return this.postService.createPost(requestBody, userId, categoryId);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<BlogAppResponse> getPostByCategory(@PathVariable("categoryId") int categoryId) {
        return this.postService.getPostByCategory(categoryId);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<BlogAppResponse> getPostByUser(@PathVariable("userId") int userId) {
        return this.postService.getPostByUser(userId);
    }

    @GetMapping("/posts/all")
    public ResponseEntity<BlogAppResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constant.POSTS_DEFAULT_SORT_FIELD, required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = Constant.DEFAULT_SORT_ORDER, required = false) String sortOrder) {
        return this.postService.getAllPost(pageNo, pageSize, sortBy, sortOrder);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<BlogAppResponse> getPostById(@PathVariable("postId") int postId) {
        return this.postService.getPostById(postId);
    }

    @GetMapping("/post/search")
    public ResponseEntity<BlogAppResponse> searchPostByKeyword(@RequestParam(value = "title") String title) {
        return this.postService.searchPostByKeyword(title);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<BlogAppResponse> deletePost(@PathVariable("postId") int postId) {
        return this.postService.deletePost(postId);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<BlogAppResponse> updatePost(@RequestBody CreatePostRequestModel requestModel,
            @PathVariable("postId") int postId) {
        return this.postService.updatePost(requestModel, postId);
    }
}
