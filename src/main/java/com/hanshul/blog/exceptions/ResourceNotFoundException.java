package com.hanshul.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException (String resourceName,String fieldName,long fieldValue) {
        super(String.format("%s not found with %s : %d",resourceName,fieldName,fieldValue));
    }
}
