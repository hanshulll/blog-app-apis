package com.hanshul.blog.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMeta <T>{
    private long took;
    private int status;
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    private T request;
//    private String event;
}
