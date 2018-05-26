package com.islack.photograph.repository;

import com.islack.photograph.domain.entity.Photograph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotographRepository extends JpaRepository<Photograph, Long> {
    List<Photograph> findByUsername(String username);
}
