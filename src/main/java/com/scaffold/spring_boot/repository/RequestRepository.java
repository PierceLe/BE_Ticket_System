package com.scaffold.spring_boot.repository;

import com.scaffold.spring_boot.enums.Cause;
import com.scaffold.spring_boot.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import com.scaffold.spring_boot.entity.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query("""
        SELECT r FROM Request r WHERE
        (:projectId IS NULL OR r.projectId = :projectId) AND
        (:creatorId IS NULL OR r.creatorId LIKE %:creatorId%) AND
        (:qaId IS NULL OR r.qaId = :qaId) AND
        (:status IS NULL OR r.status = :status) AND
        (:assignedId IS NULL OR r.assignedId = :assignedId)
    """)
    List<Request> findRequestsByFilters(
            @Param("projectId") Integer projectId,
            @Param("creatorId") String creatorId,
            @Param("qaId") String qaId,
            @Param("status") Status status,
            @Param("assignedId") String assignedId
    );
}
