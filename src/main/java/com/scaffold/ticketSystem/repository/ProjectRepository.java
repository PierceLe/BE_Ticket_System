package com.scaffold.ticketSystem.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scaffold.ticketSystem.entity.Project;

import lombok.NonNull;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Override
    @NonNull
    Optional<Project> findById(@NonNull Integer id);

    Optional<Project> findByName(@NonNull String name);

    Boolean existsByName(@NonNull String name);

    @Query(value = "SELECT p FROM Project p")
    Page<Project> listProjects(Pageable pageable);
}
