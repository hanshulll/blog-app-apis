package com.hanshul.blog.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private String about;
}
