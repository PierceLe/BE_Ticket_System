package com.scaffold.spring_boot.repository;

import com.scaffold.spring_boot.entity.Project;
import com.scaffold.spring_boot.entity.Unit;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Override
    @NonNull
    Optional<Project> findById(@NonNull Integer id);
    Optional<Project> findByName(@NonNull String name);
    Boolean existsByName(@NonNull String name);
}
