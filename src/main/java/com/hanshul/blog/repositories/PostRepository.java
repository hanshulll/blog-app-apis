package com.hanshul.blog.repositories;

import com.hanshul.blog.entities.CategoryEntity;
import com.hanshul.blog.entities.UserEntity;
import com.hanshul.blog.entities.UserPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<UserPostEntity, Integer> {
    List<UserPostEntity> findByUser(UserEntity user);
    List<UserPostEntity> findByCategory(CategoryEntity category);
    @Query("SELECT p FROM UserPostEntity p")
    List<UserPostEntity> findTop100Posts(Pageable pageable);
}
