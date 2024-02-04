package com.hanshul.blog.config;

import com.hanshul.blog.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration

public class Configuration {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

//    @Bean
//    public UserEntity userEntity(){
//        return new UserEntity();
//    }
}
