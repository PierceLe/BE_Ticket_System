package com.scaffold.spring_boot.repository;

import com.scaffold.spring_boot.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Boolean existsByUsername(String username);
    Users findByUsername(String username);
}
