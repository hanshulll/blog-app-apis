package com.hanshul.blog.service;

import com.hanshul.blog.payloads.UserDto;
import com.hanshul.blog.utility.BlogAppResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<BlogAppResponse> createUser(UserDto userDto);
    ResponseEntity<BlogAppResponse> updateUser(UserDto userDto, Integer userId);
    ResponseEntity<BlogAppResponse> getUserById(Integer userId);
    ResponseEntity<BlogAppResponse> getAllUsers();
    void deleteUser(Integer userId);
}
