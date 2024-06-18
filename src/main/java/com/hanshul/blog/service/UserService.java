package com.hanshul.blog.service;

import com.hanshul.blog.payloads.UserDetailRequestModel;
import com.hanshul.blog.utility.BlogAppResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<BlogAppResponse> createUser(UserDetailRequestModel userDto);
    ResponseEntity<BlogAppResponse> updateUser(UserDetailRequestModel userDto, Integer userId);
    ResponseEntity<BlogAppResponse> getUserById(Integer userId);
    ResponseEntity<BlogAppResponse> getAllUsers();
    void deleteUser(Integer userId);
}
