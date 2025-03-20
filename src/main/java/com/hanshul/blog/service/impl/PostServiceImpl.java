package com.hanshul.blog.service.impl;

import com.hanshul.blog.dto.PostDto;
import com.hanshul.blog.entities.CategoryEntity;
import com.hanshul.blog.entities.UserEntity;
import com.hanshul.blog.entities.UserPostEntity;
import com.hanshul.blog.exceptions.ResourceNotFoundException;
import com.hanshul.blog.payloads.CreatePostRequestModel;
import com.hanshul.blog.repositories.CategoryRepository;
import com.hanshul.blog.repositories.PostRepository;
import com.hanshul.blog.repositories.UserRepository;
import com.hanshul.blog.service.PostService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
    public ResponseEntity<BlogAppResponse> createPost(CreatePostRequestModel requestBody, Integer userId,
            Integer categoryId) {
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
        Instant startTime = Instant.now();
        Pageable pageable = PageRequest.of(0, 100);
        List<UserPostEntity> allPosts = this.postRepository.findTop100Posts(pageable);
        List<PostDto> allPostsData = this.mapUserPostEntityToDto.apply(allPosts);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).data(allPostsData).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getPostById(Integer postId) {
        return null;
    }

    @Override
    public ResponseEntity<BlogAppResponse> getPostByCategory(Integer categoryId) {
        Instant startTime = Instant.now();
        CategoryEntity category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        List<UserPostEntity> postsEntity = this.postRepository.findByCategory(category);
        List<PostDto> postDto = this.mapUserPostEntityToDto.apply(postsEntity);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).data(postDto).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getPostByUser(Integer userId) {
        Instant startTime = Instant.now();
        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        List<UserPostEntity> postsEntity = this.postRepository.findByUser(user);
        List<PostDto> postDto = this.mapUserPostEntityToDto.apply(postsEntity);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).data(postDto).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BlogAppResponse> searchPostByKeyword(String keyword) {
        return null;
    }

    /////////////////////////////////////////////////
    ///// PRIVATE METHODS || HELPER METHODS
    /////////////////////////////////////////////////

    Function<List<UserPostEntity>, List<PostDto>> mapUserPostEntityToDto = (userPostEntities -> userPostEntities
            .stream().map(post -> this.modelMapper.map(post, PostDto.class)).toList());
}
