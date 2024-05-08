package com.hanshul.blog.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 3, message = "username must be minimum of 3 characters")
    private String name;
    @Email(message = "Your email is not valid")
    private String email;
    @NotEmpty
    @Size(min = 3,max = 10, message = "Password must be minimum of 3 characters and maximum of 10 characters")
    private String password;
    @NotEmpty
    private String about;
}
