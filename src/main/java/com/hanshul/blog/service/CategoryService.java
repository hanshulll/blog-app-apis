package com.hanshul.blog.service;

import com.hanshul.blog.payloads.CategoryDetailRequestModel;
import com.hanshul.blog.utility.BlogAppResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<BlogAppResponse> createCategory(CategoryDetailRequestModel categoryDto);
    ResponseEntity<BlogAppResponse> updateCategory(CategoryDetailRequestModel categoryDto,Integer categoryId);
    void  deleteCategory(Integer categoryId);
    ResponseEntity<BlogAppResponse> getCategoryById(Integer categoryId);
    ResponseEntity<BlogAppResponse> getAllCategories();
}
