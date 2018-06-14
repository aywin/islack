package com.islack.photograph.repository;

import com.islack.photograph.domain.entity.Category;
import com.islack.photograph.domain.entity.Photograph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByTitleIn(String[] categories);
    Category findBySlug(String slug);
}
