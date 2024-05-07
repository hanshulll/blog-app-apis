package com.hanshul.blog.payloads;

import lombok.Getter;
@Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private int id;
    private String name;
    private String email;
    @com.fasterxml.jackson.annotation.JsonIgnore
    private String password;
    private String about;
}
