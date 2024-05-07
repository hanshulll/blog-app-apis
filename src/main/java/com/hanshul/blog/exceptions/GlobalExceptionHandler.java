package com.hanshul.blog.exceptions;

import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<BlogAppResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        Instant startTime = Instant.now();
        BlogAppResponse response = BlogAppResponse.builder().success(false).starTime(startTime).data(exception.getMessage())
                .meta(ResponseMeta.builder().status(HttpStatus.NOT_FOUND.value()).build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
     }
}
