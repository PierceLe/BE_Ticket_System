package com.scaffold.spring_boot.repository;

import com.scaffold.spring_boot.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Boolean existsByUsername(String username);
    Users findByUsername(String username);
    Boolean existsByEmail(String email);
    List<Users> findByUnitId(Integer unitId);

    @Query("SELECT u FROM Users u WHERE " +
            "(:username IS NULL OR u.username LIKE %:username%) AND " +
            "(:unitId IS NULL OR u.unitId = :unitId) AND " +
            "(:role IS NULL OR u.role = :role) AND " +
            "(:email IS NULL OR u.email LIKE %:email%) AND " +
            "(:fullName IS NULL OR u.fullName LIKE %:fullName%) AND " +
            "(:dob IS NULL OR u.dob = :dob)")
    List<Users> findUsersByFilters(
            @Param("username") String username,
            @Param("unitId") Integer unitId,
            @Param("role") String role,
            @Param("email") String email,
            @Param("fullName") String fullName,
            @Param("dob") LocalDate dob
    );
}
