package com.hanshul.blog.repositories;

import com.hanshul.blog.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageUploadRepository extends JpaRepository<ImageEntity, Integer> {
}
