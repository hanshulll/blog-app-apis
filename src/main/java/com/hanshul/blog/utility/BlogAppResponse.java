package com.hanshul.blog.utility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Duration;
import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlogAppResponse<T, M> {

    private boolean success;

//    private String id;

    private ResponseMeta<M> meta;

    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    private T data;

    @JsonIgnore
    private Instant starTime;

    public static class BlogAppResponseBuilder<T,M> {
        public BlogAppResponseBuilder meta(ResponseMeta<M> meta){
            ResponseMeta responseMeta = new ResponseMeta<>();
            responseMeta.setStatus(meta.getStatus());
            responseMeta.setTook(Duration.between(starTime,Instant.now()).toMillis());
            responseMeta.setRequest(meta.getRequest());
//            responseMeta.setEvent(meta.getEvent());
            this.meta = meta;
            return this;
        }
    }
}
