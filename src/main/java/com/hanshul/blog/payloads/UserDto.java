package com.hanshul.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
@Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private int id;
    @NotNull
    private String name;
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String about;
}
