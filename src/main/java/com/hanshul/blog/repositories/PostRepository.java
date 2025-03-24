package com.hanshul.blog.repositories;

import com.hanshul.blog.entities.CategoryEntity;
import com.hanshul.blog.entities.UserEntity;
import com.hanshul.blog.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUser(UserEntity user);
    List<PostEntity> findByCategory(CategoryEntity category);
    List<PostEntity> findByTitleContaining(String keyword);
    // @Query("SELECT p FROM UserPostEntity p")
    // List<UserPostEntity> findTop100Posts(Pageable pageable);
}
