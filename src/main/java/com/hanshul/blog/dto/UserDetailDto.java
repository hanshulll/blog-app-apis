package com.hanshul.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hanshul.blog.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailDto {
    private int id;
    private String name;
    private String email;
    private String about;
    private Set<RoleEntity> roles;
}
