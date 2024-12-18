package com.scaffold.spring_boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scaffold.spring_boot.entity.Unit;

import lombok.NonNull;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
    @Override
    @NonNull
    Optional<Unit> findById(@NonNull Integer id);

    Optional<Unit> findByName(@NonNull String name);

    Boolean existsByName(@NonNull String name);
}
