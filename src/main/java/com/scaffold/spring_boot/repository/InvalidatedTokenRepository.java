package com.scaffold.spring_boot.repository;

import com.scaffold.spring_boot.entity.InvalidatedToken;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {

}
