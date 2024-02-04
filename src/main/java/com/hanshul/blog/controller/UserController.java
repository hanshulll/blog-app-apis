package com.hanshul.blog.controller;

import com.hanshul.blog.payloads.UserDto;
import com.hanshul.blog.service.UserService;
import com.hanshul.blog.utility.BlogAppResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<BlogAppResponse> createUser(@RequestBody UserDto userDto){
        return this.userService.createUser(userDto);
    }
}
