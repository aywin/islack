package com.islack.photograph.repository;

import com.islack.photograph.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface TagRepositorySpring extends JpaRepository<Tag, String> {

}
