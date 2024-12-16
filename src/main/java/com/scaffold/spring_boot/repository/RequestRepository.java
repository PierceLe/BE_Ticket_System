package com.scaffold.spring_boot.repository;


import com.scaffold.spring_boot.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {

}
