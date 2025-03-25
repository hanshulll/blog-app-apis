package com.hanshul.blog.controller;

import com.hanshul.blog.payloads.UserDetailRequestModel;
import com.hanshul.blog.service.UserService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "API under this section handles requests related to Users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // @PostMapping("/create")
    // public ResponseEntity<BlogAppResponse> createUser(
    // @Valid @RequestBody UserDetailRequestModel userDetailRequestModel) {
    // return this.userService.createUser(userDetailRequestModel);
    // }
    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/register")
    @Operation(summary = "API to register a new user", description = "This API registers a new user in the system")
    public ResponseEntity<BlogAppResponse> registerUser(@RequestBody UserDetailRequestModel request) {
        return this.userService.registerUser(request);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/{userId}/update")
    @Operation(summary = "API to update user details", description = "This API updates users details")
    public ResponseEntity<BlogAppResponse> updateUser(@RequestBody UserDetailRequestModel userDetailRequestModel,
            @PathVariable("userId") Integer userId) {
        return this.userService.updateUser(userDetailRequestModel, userId);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{userId}/delete")
    @Operation(summary = "API to delete a user", description = "This API deletes a users but only a admin can do so")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.ok(BlogAppResponse.builder().success(true).starTime(Instant.now())
                .data(Map.of("message", "User deleted successfully"))
                .meta(ResponseMeta.builder().status(HttpStatus.OK.value()).build()).build());
    }

    @GetMapping("/{userId}/get")
    @Operation(summary = "API to get user details by userId", description = "This API fetches users details by userId")
    public ResponseEntity<BlogAppResponse> getUserById(@PathVariable("userId") Integer userId) {
        return this.userService.getUserById(userId);
    }

    @GetMapping("/get/all")
    @Operation(summary = "API to fetch all Users", description = "This API fetches all available users no pagination for now unlike getAllPostAPi will implement soon")
    public ResponseEntity<BlogAppResponse> getAllUsers() {
        return this.userService.getAllUsers();
    }
}
