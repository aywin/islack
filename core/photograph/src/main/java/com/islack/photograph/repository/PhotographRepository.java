package com.islack.photograph.repository;

import com.islack.photograph.domain.entity.Photograph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotographRepository extends JpaRepository<Photograph, Long> {
}
