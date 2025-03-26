package com.hanshul.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private CategoryDto category;
    private UserDetailDto user;
    private Set<CommentDto> comments;
}
