package com.hanshul.blog.service.impl;

import com.hanshul.blog.dto.PostDto;
import com.hanshul.blog.entities.CategoryEntity;
import com.hanshul.blog.entities.UserEntity;
import com.hanshul.blog.entities.UserPostEntity;
import com.hanshul.blog.exceptions.ResourceNotFoundException;
import com.hanshul.blog.repositories.CategoryRepository;
import com.hanshul.blog.repositories.PostRepository;
import com.hanshul.blog.repositories.UserRepository;
import com.hanshul.blog.service.PostService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {

    /////////////////////////////////////////////////
    ///// VARIABLES
    /////////////////////////////////////////////////

    private PostRepository postRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    /////////////////////////////////////////////////
    /////// CONSTRUCTOR
    /////////////////////////////////////////////////

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository,
            CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    /////////////////////////////////////////////////
    /////// METHODS
    /////////////////////////////////////////////////

    @Override
    public ResponseEntity<BlogAppResponse> createPost(PostDto requestBody, Integer userId, Integer categoryId) {
        Instant startTime = Instant.now();
        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        CategoryEntity category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        UserPostEntity post = UserPostEntity.builder().content(requestBody.getContent()).title(requestBody.getTitle())
                .imageName("default.png").addedDate(new Date()).user(user).category(category).build();
        UserPostEntity savedPost = this.postRepository.save(post);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.CREATED.value()).request(requestBody).build())
                .data(Map.of("message",
                        String.format("Post created successfully with post_id %s", savedPost.getPostId())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BlogAppResponse> updatePost(PostDto createPostRequestBody, Integer postId) {
        return null;
    }

    @Override
    public ResponseEntity<BlogAppResponse> deletePost(Integer postId) {
        return null;
    }

    @Override
    public ResponseEntity<BlogAppResponse> getAllPost() {
        return null;
    }

    @Override
    public ResponseEntity<BlogAppResponse> getPostById(Integer postId) {
        return null;
    }

    @Override
    public ResponseEntity<BlogAppResponse> getPostByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public ResponseEntity<BlogAppResponse> getPostByUser(Integer userId) {
        return null;
    }

    @Override
    public ResponseEntity<BlogAppResponse> searchPostByKeyword(String keyword) {
        return null;
    }
}
