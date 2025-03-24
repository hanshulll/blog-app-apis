package com.hanshul.blog.config;

import com.hanshul.blog.entities.RoleEntity;
import com.hanshul.blog.repositories.RoleRepository;
import com.hanshul.blog.utility.Constant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultRolesConfig {

    @Bean
    public CommandLineRunner addDefaultRoles(RoleRepository roleRepository) {
        return args -> {
            // Check if roles already exist to avoid duplication
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                RoleEntity userRole = new RoleEntity();
                userRole.setName("ROLE_USER");
                userRole.setId(Constant.NORMAL_USER_ID);
                roleRepository.save(userRole);
            }

            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                RoleEntity userRole = new RoleEntity();
                userRole.setName("ROLE_ADMIN");
                userRole.setId(Constant.ADMIN_USER_ID);
                roleRepository.save(userRole);
            }
        };
    }
}
