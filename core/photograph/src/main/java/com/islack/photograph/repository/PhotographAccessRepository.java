package com.islack.photograph.repository;

import com.islack.photograph.domain.entity.PhotographAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotographAccessRepository extends JpaRepository<PhotographAccess, String> {
    List<PhotographAccess> findByUsername(String username);
}
