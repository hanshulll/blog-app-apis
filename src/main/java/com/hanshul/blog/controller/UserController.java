package com.hanshul.blog.controller;

import com.hanshul.blog.payloads.UserDto;
import com.hanshul.blog.service.UserService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<BlogAppResponse> createUser(@Valid @RequestBody UserDto userDto){
        return this.userService.createUser(userDto);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<BlogAppResponse> updateUser(@RequestBody UserDto userDto,@PathVariable("userId") Integer userId){
        return this.userService.updateUser(userDto,userId);
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
        this.userService.deleteUser(userId);
        return ResponseEntity.ok(BlogAppResponse.builder().success(true).starTime(Instant.now()).
                data(Map.of("message","User deleted successfully"))
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value())
                        .build()).build());
    }

    @GetMapping("/{userId}/get")
    public ResponseEntity<BlogAppResponse> getUserById(@PathVariable("userId") Integer userId){
        return this.userService.getUserById(userId);
    }

    @GetMapping("/get/all")
    public ResponseEntity<BlogAppResponse> getAllUsers(){
        return this.userService.getAllUsers();
    }
}
