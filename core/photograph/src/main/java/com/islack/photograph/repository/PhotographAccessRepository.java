package com.islack.photograph.repository;

import com.islack.photograph.domain.entity.Photograph;
import com.islack.photograph.domain.entity.PhotographAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotographAccessRepository extends JpaRepository<PhotographAccess, Long> {
    List<PhotographAccess> findByUsername(String username);
    Optional<PhotographAccess> findByUsernameAndPhotograph(String username, Photograph p);
}
