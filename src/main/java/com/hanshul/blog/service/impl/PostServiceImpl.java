package com.hanshul.blog.service.impl;

import com.hanshul.blog.dto.PaginationMetaDto;
import com.hanshul.blog.dto.PostDto;
import com.hanshul.blog.entities.CategoryEntity;
import com.hanshul.blog.entities.ImageEntity;
import com.hanshul.blog.entities.UserEntity;
import com.hanshul.blog.entities.UserPostEntity;
import com.hanshul.blog.exceptions.ResourceNotFoundException;
import com.hanshul.blog.payloads.CreatePostRequestModel;
import com.hanshul.blog.repositories.CategoryRepository;
import com.hanshul.blog.repositories.ImageUploadRepository;
import com.hanshul.blog.repositories.PostRepository;
import com.hanshul.blog.repositories.UserRepository;
import com.hanshul.blog.service.PostService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    /// //////////////////////////////////////////////
    /// // VARIABLES
    /// //////////////////////////////////////////////

    private PostRepository postRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private ImageUploadRepository imageRepository;

    /// //////////////////////////////////////////////
    /// //// CONSTRUCTOR
    /// //////////////////////////////////////////////

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository,
            CategoryRepository categoryRepository, ImageUploadRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.imageRepository = imageRepository;
    }

    /// //////////////////////////////////////////////
    /// //// METHODS
    /// //////////////////////////////////////////////

    @Transactional
    @Override
    public ResponseEntity<BlogAppResponse> createPost(CreatePostRequestModel requestBody, Integer userId,
            Integer categoryId, List<MultipartFile> files) {
        Instant startTime = Instant.now();
        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        CategoryEntity category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        UserPostEntity post = UserPostEntity.builder().content(requestBody.getContent()).title(requestBody.getTitle())
                .user(user).category(category).build();
        UserPostEntity savedPost = this.postRepository.save(post);
        if (!files.isEmpty()) {
            List<ImageEntity> populatedImageEntities = this.populateImageEntity(files, savedPost);
            this.imageRepository.saveAll(populatedImageEntities);
        }
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.CREATED.value()).request(requestBody).build())
                .data(Map.of("message",
                        String.format("Post created successfully with post_id %s", savedPost.getPostId())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BlogAppResponse> updatePost(CreatePostRequestModel createPostRequestBody, Integer postId) {
        Instant startTime = Instant.now();
        UserPostEntity postToBeUpdated = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        postToBeUpdated.setTitle(createPostRequestBody.getTitle());
        postToBeUpdated.setContent(createPostRequestBody.getContent());
        this.postRepository.save(postToBeUpdated);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build())
                .data(Map.of("status", String.format("Request to update post with postId %s is received", postId)))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BlogAppResponse> deletePost(Integer postId) {
        Instant startTime = Instant.now();
        UserPostEntity postToDelete = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        this.postRepository.delete(postToDelete);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build())
                .data(Map.of("status", String.format("Request to delete post with postId %s is received", postId)))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getAllPost(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Instant startTime = Instant.now();
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                "asc".equalsIgnoreCase(sortOrder) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<UserPostEntity> allPosts = this.postRepository.findAll(pageable);
        List<PostDto> allPostsData = this.mapUserPostEntityToDto.apply(allPosts.getContent());
        PaginationMetaDto paginationMeta = this.populatePaginationMetaDto.apply(allPosts);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build())
                .data(Map.of("posts", allPostsData, "pageMeta", paginationMeta)).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getPostById(Integer postId) {
        Instant startTime = Instant.now();
        UserPostEntity userPosts = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        PostDto post = this.modelMapper.map(userPosts, PostDto.class);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).data(post).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getPostByCategory(Integer categoryId) {
        Instant startTime = Instant.now();
        CategoryEntity category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", "Category Id", categoryId));
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
                .orElseThrow(() -> new ResourceNotFoundException("Post", "User Id", userId));
        List<UserPostEntity> postsEntity = this.postRepository.findByUser(user);
        List<PostDto> postDto = this.mapUserPostEntityToDto.apply(postsEntity);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).data(postDto).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BlogAppResponse> searchPostByKeyword(String keyword) {
        Instant startTime = Instant.now();
        List<UserPostEntity> postEntities = this.postRepository.findByTitleContaining(keyword);
        List<PostDto> postDto = this.mapUserPostEntityToDto.apply(postEntities);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).data(postDto).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /// //////////////////////////////////////////////
    /// // PRIVATE METHODS || HELPER METHODS
    /// //////////////////////////////////////////////

    Function<List<UserPostEntity>, List<PostDto>> mapUserPostEntityToDto = (userPostEntities -> userPostEntities
            .stream().map(post -> this.modelMapper.map(post, PostDto.class)).toList());

    Function<Page<UserPostEntity>, PaginationMetaDto> populatePaginationMetaDto = pageMeta -> PaginationMetaDto
            .builder().currentPageNumber(pageMeta.getNumber()).currentPageSize(pageMeta.getSize())
            .totalElements(pageMeta.getTotalElements()).totalPages(pageMeta.getTotalPages() - 1)
            .lastPage(pageMeta.isLast()).build();

    private List<ImageEntity> populateImageEntity(List<MultipartFile> postImages, UserPostEntity post) {
        return postImages.stream().map(imageData -> {
            try {
                return ImageEntity.builder().image(imageData.getBytes()).fileName(imageData.getOriginalFilename())
                        .fileType(imageData.getContentType()).post(post).build();
            } catch (IOException e) {
                log.error("Error occurred while creating ImageEntity for file: {} - {}",
                        imageData.getOriginalFilename(), e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
