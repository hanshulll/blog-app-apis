package com.hanshul.blog.service.impl;

import com.hanshul.blog.dto.CategoryDto;
import com.hanshul.blog.entities.CategoryEntity;
import com.hanshul.blog.exceptions.ResourceNotFoundException;
import com.hanshul.blog.payloads.CategoryDetailRequestModel;
import com.hanshul.blog.repositories.CategoryRepository;
import com.hanshul.blog.service.CategoryService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    /////////////////////////////////////////////////
    ///// VARIABLES
    /////////////////////////////////////////////////
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);


    /////////////////////////////////////////////////
    /////// CONSTRUCTOR
    /////////////////////////////////////////////////

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository category) {
        this.modelMapper = modelMapper;
        this.categoryRepository = category;
    }

    /////////////////////////////////////////////////
    /////// METHODS
    /////////////////////////////////////////////////
    
    @Override
    public ResponseEntity<BlogAppResponse> createCategory(CategoryDetailRequestModel categoryDetailRequestModel) {
        LOGGER.debug("Inside createCategory() method of CategoryServiceImpl");
        Instant startTime = Instant.now();
        CategoryEntity entity = this.modelMapper.map(categoryDetailRequestModel,CategoryEntity.class);
        CategoryEntity createdCategory = this.categoryRepository.save(entity);

        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().request(categoryDetailRequestModel).build())
                .data(Map.of("message",String.format("category created successfully with id %s",createdCategory.getId())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BlogAppResponse> updateCategory(CategoryDetailRequestModel categoryDetailRequestModel, Integer categoryId) {
        LOGGER.debug("Inside updateCategory() method of CategoryServiceImpl, categoryId : {}",categoryId);
        Instant startTime = Instant.now();
        this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        CategoryEntity categoryEntity = this.modelMapper.map(categoryDetailRequestModel,CategoryEntity.class);
        this.categoryRepository.save(categoryEntity);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .meta(ResponseMeta.builder().request(categoryDetailRequestModel).build())
                .data(Map.of("message",String.format("Category details successfully updated with id %s",categoryId)))
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        LOGGER.debug("Inside deleteCategory() method of CategoryServiceImpl, categoryId : {}",categoryId);
        this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        this.categoryRepository.deleteById(categoryId);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getCategoryById(Integer categoryId) {
        LOGGER.debug("Inside getCategoryById() method of CategoryServiceImpl, categoryId : {}",categoryId);
        Instant startTime = Instant.now();
        CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        CategoryDto categoryDto = this.modelMapper.map(categoryEntity, CategoryDto.class);
        BlogAppResponse response = BlogAppResponse.builder().success(true).starTime(startTime)
                .data(categoryDto)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BlogAppResponse> getAllCategories() {
        Instant startTime = Instant.now();
        List<CategoryEntity> userEntity = this.categoryRepository.findAll();
        List<CategoryDto> response = userEntity.stream().map(this::mapCategoryrEntityToCategoryDto).collect(Collectors.toList());
        return ResponseEntity.ok(BlogAppResponse.builder().success(true).starTime(startTime)
                .data(response)
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build())
                .build());
    }

    ///////////////////////////////////////////////////
    /////// PRIVATE METHODS
    ///////////////////////////////////////////////////

    private CategoryDto mapCategoryrEntityToCategoryDto(CategoryEntity categoryEntity) {
        return this.modelMapper.map(categoryEntity,CategoryDto.class);
    }
}
