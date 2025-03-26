package com.hanshul.blog.controller;

import com.hanshul.blog.payloads.CategoryDetailRequestModel;
import com.hanshul.blog.service.CategoryService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Category Controller", description = "API under this section handles requests related to blog categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/create")
    @Operation(summary = "API to create categories for posts", description = "This api created new categories for posts")
    public ResponseEntity<BlogAppResponse> createCategory(
            @Valid @RequestBody CategoryDetailRequestModel categoryDetailRequestModel) {
        return this.categoryService.createCategory(categoryDetailRequestModel);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/{categoryId}/update")
    @Operation(summary = "API to update categories details for posts", description = "This api update existing categories details for posts")
    public ResponseEntity<BlogAppResponse> updateCategory(
            @RequestBody CategoryDetailRequestModel categoryDetailRequestModel,
            @PathVariable("userId") Integer userId) {
        return this.categoryService.updateCategory(categoryDetailRequestModel, userId);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}/delete")
    @Operation(summary = "API to delete categories", description = "This api deletes existing categories")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(BlogAppResponse.builder().success(true).starTime(Instant.now())
                .data(Map.of("message", "User deleted successfully"))
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).build());
    }

    @GetMapping("/{categoryId}/get")
    @Operation(summary = "API to get category by categoryId", description = "This api fetches category details by categoryId")
    public ResponseEntity<BlogAppResponse> getCategoryById(@PathVariable("categoryId") Integer userId) {
        return this.categoryService.getCategoryById(userId);
    }

    @GetMapping("/get/all")
    @Operation(summary = "API to fetch all available categories", description = "This api fetched all available categories")
    public ResponseEntity<BlogAppResponse> getAllCategories() {
        return this.categoryService.getAllCategories();
    }
}
