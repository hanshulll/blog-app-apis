package com.hanshul.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequestModel {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
