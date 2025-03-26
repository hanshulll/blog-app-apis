package com.hanshul.blog.repositories;

import com.hanshul.blog.entities.ImageEntity;
import com.hanshul.blog.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageUploadRepository extends JpaRepository<ImageEntity, Integer> {
    List<ImageEntity> findByPost(PostEntity post);
}
