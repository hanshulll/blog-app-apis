package com.hanshul.blog.exceptions;

import com.hanshul.blog.utility.BlogAppResponse;
import com.hanshul.blog.utility.ResponseMeta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BlogAppResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        Instant startTime = Instant.now();
        BlogAppResponse response = BlogAppResponse.builder().success(false).starTime(startTime)
                .data(exception.getMessage()).meta(ResponseMeta.builder().status(HttpStatus.NOT_FOUND.value()).build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        Map<String, String> response = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            response.put(((FieldError) error).getField(), error.getDefaultMessage());
        });
        response.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }
}
