package com.scaffold.spring_boot.repository;

import com.scaffold.spring_boot.entity.Unit;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
    @Override
    @NonNull
    Optional<Unit> findById(@NonNull Integer id);
}
