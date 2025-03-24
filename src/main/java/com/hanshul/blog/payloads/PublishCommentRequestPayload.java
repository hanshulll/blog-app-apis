package com.hanshul.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublishCommentRequestPayload {
    @NotEmpty
    private int userId;
    @NotBlank
    private int postId;
    @NotBlank
    private String content;
}
