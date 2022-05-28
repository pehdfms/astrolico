package com.makers.astrolico.repository;

import com.makers.astrolico.entity.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "planets", path = "planets")
public interface PlanetRepository extends JpaRepository<Planet, Long> {
    @Query(
            value = "SELECT * FROM planets p WHERE LOWER(p.title) LIKE %:title%",
            countQuery = "SELECT count(*) FROM planets p WHERE LOWER(p.title) LIKE %:title%",
            nativeQuery = true
    )
    @RestResource(path = "findByTitle", rel = "findByTitle")
    Page<Planet> searchByTitleLike(@Param("title") String title, Pageable pageable);
}
