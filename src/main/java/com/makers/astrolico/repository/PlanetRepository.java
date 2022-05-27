package com.makers.astrolico.repository;

import com.makers.astrolico.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "planets", path = "planets")
public interface PlanetRepository extends JpaRepository<Planet, Long> {
    @RestResource(path = "findByTitle", rel = "findByTitle")
    Planet findFirstByTitle(String title);
}
