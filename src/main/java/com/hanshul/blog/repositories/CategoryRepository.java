package com.hanshul.blog.repositories;

import com.hanshul.blog.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {

}
