package com.hanshul.blog.controller;

import com.hanshul.blog.payloads.CategoryDetailRequestModel;
import com.hanshul.blog.service.CategoryService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<BlogAppResponse> createCategory(
            @Valid @RequestBody CategoryDetailRequestModel categoryDetailRequestModel) {
        return this.categoryService.createCategory(categoryDetailRequestModel);
    }

    @PutMapping("/{categoryId}/update")
    public ResponseEntity<BlogAppResponse> updateCategory(
            @RequestBody CategoryDetailRequestModel categoryDetailRequestModel,
            @PathVariable("userId") Integer userId) {
        return this.categoryService.updateCategory(categoryDetailRequestModel, userId);
    }

    @DeleteMapping("/{categoryId}/delete")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(BlogAppResponse.builder().success(true).starTime(Instant.now())
                .data(Map.of("message", "User deleted successfully"))
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).build());
    }

    @GetMapping("/{categoryId}/get")
    public ResponseEntity<BlogAppResponse> getCategoryById(@PathVariable("userId") Integer userId) {
        return this.categoryService.getCategoryById(userId);
    }

    @GetMapping("/get/all")
    public ResponseEntity<BlogAppResponse> getAllCategories() {
        return this.categoryService.getAllCategories();
    }
}
