package com.islack.photograph.repository;

import com.islack.photograph.domain.entity.Photograph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhotographRepository extends JpaRepository<Photograph, Long> {
    List<Photograph> findByUsername(String username);
    Optional<Photograph> findByUsernameAndId(String username, Long id);
    @Query("SELECT p FROM Photograph p join p.tags t WHERE t.tag IN (SELECT t2 FROM Photograph p2 join p2.tags t2 WHERE p2.id = :photograph)" +
            " AND p.id <> :photograph GROUP BY p ORDER BY COUNT(p) DESC")
    Page<Photograph> findRecommended(@Param("photograph") Long photograph, Pageable pageable);
    @Query("SELECT p FROM Photograph p join p.tags t WHERE t.tag IN (SELECT t2 FROM Photograph p2 join p2.tags t2 WHERE p2.id IN (:list))" +
            " AND p.id NOT IN (:list) GROUP BY p ORDER BY COUNT(p) DESC")
    Page<Photograph> findRecommended(@Param("list") List<Long> list, Pageable pageable);
    @Query("SELECT p FROM Photograph p left join p.photographAccesses a GROUP BY p ORDER BY COUNT(a) DESC")
    Page<Photograph> findPopular(Pageable pageable);
    Page<Photograph> findByCategoriesSlug(String slug, Pageable pageable);
}
