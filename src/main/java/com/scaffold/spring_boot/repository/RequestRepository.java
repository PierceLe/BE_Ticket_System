package com.scaffold.spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scaffold.spring_boot.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {}
