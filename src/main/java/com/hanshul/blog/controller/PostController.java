package com.hanshul.blog.controller;

import com.hanshul.blog.payloads.CreatePostRequestModel;
import com.hanshul.blog.service.PostService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Post Controller", description = "API under this section handles requests related to Users Posts")
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

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping(value = "/user/{userId}/category/{categoryId}/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "API to create Post", description = "This api creates a Post for a user")
    public ResponseEntity<BlogAppResponse> createPost(@PathVariable("userId") Integer userId,
            @PathVariable("categoryId") Integer categoryId, @Valid @RequestPart CreatePostRequestModel requestBody,
            @Valid @Size(max = 5) @RequestPart(required = false) List<MultipartFile> files) {
        return this.postService.createPost(requestBody, userId, categoryId, files);
    }

    @GetMapping("/category/{categoryId}/posts")
    @Operation(summary = "API to get post by category", description = "This api gets all Post for a single category by categoryId")
    public ResponseEntity<BlogAppResponse> getPostByCategory(@PathVariable("categoryId") int categoryId) {
        return this.postService.getPostByCategory(categoryId);
    }

    @GetMapping("/user/{userId}/posts")
    @Operation(summary = "API to get post by User", description = "This api gets all Post for a user by userId")
    public ResponseEntity<BlogAppResponse> getPostByUser(@PathVariable("userId") int userId) {
        return this.postService.getPostByUser(userId);
    }

    @GetMapping("/posts/all")
    @Operation(summary = "API to get all post with pagination", description = "This api gets all posts by default with postId sorted in descending order if no query parameters are present")
    public ResponseEntity<BlogAppResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constant.POSTS_DEFAULT_SORT_FIELD, required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = Constant.DEFAULT_SORT_ORDER, required = false) String sortOrder) {
        return this.postService.getAllPost(pageNo, pageSize, sortBy, sortOrder);
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "API to get a post by postId", description = "This api gets a Post by postId")
    public ResponseEntity<BlogAppResponse> getPostById(@PathVariable("postId") int postId) {
        return this.postService.getPostById(postId);
    }

    @GetMapping("/post/search")
    @Operation(summary = "API to search for specified words in post titles", description = "This API searches for the provided character sequence in the titles of posts stored in the database and returns the matching posts.")
    public ResponseEntity<BlogAppResponse> searchPostByKeyword(@RequestParam(value = "postTitle") String title) {
        return this.postService.searchPostByKeyword(title);
    }

    @GetMapping("/user/{userId}/post/{postId}/media")
    @Operation(summary = "API to fetch all media for a post", description = "This API fetches all the media for a post")
    public ResponseEntity<BlogAppResponse> fetchAllPostMedia(@PathVariable(value = "userId") int userId,
            @PathVariable(value = "postId") int postId) {
        return this.postService.fetchAllPostMedia(userId, postId);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/post/{postId}")
    @Operation(summary = "API to delete a post", description = "This API deletes a post")
    public ResponseEntity<BlogAppResponse> deletePost(@PathVariable("postId") int postId) {
        return this.postService.deletePost(postId);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/post/{postId}")
    @Operation(summary = "API to update a post", description = "This API updates a post")
    public ResponseEntity<BlogAppResponse> updatePost(@RequestBody CreatePostRequestModel requestModel,
            @PathVariable("postId") int postId) {
        return this.postService.updatePost(requestModel, postId);
    }
}
