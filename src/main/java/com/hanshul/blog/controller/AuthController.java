package com.hanshul.blog.controller;

import com.hanshul.blog.dto.JwtResponseDto;
import com.hanshul.blog.payloads.JwtAuthRequest;
import com.hanshul.blog.payloads.UserDetailRequestModel;
import com.hanshul.blog.security.JwtTokenHelper;
import com.hanshul.blog.security.MyUserDetailsService;
import com.hanshul.blog.service.UserService;
import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authorization Controller", description = "API under this section handles requests related to authorization")
public class AuthController {

    private JwtTokenHelper jwtTokenHelper;
    private MyUserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private UserService userService;

    public AuthController(JwtTokenHelper jwtTokenHelper, MyUserDetailsService userDetailsService, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "API to authenticate user and generate JWT token", description = "This api receives user info authenticate it and returns JWT token on successful authentication")
    public ResponseEntity<BlogAppResponse> createToken(@RequestBody JwtAuthRequest request) {
        Instant startTime = Instant.now();
        this.authenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtTokenHelper.generateToken(userDetails.getUsername());
        BlogAppResponse response = BlogAppResponse.builder().success(true)
                .starTime(startTime).meta(ResponseMeta.builder().status(200).build()).data(new JwtResponseDto(token)).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<BlogAppResponse> registerUser(@RequestBody UserDetailRequestModel request) {
        return this.userService.registerUser(request);
    }

    private void authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        this.authenticationManager.authenticate(authenticationToken);
    }
}
