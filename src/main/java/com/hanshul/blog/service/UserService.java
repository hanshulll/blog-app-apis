package com.hanshul.blog.service;

import com.hanshul.blog.payloads.UserDto;
import com.hanshul.blog.utility.BlogAppResponse;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse;
import java.util.List;

public interface UserService {
    ResponseEntity<BlogAppResponse> createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
