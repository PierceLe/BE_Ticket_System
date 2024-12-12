package com.scaffold.spring_boot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "requests")
public class Request {
    @Id
    Integer id;
    String title;
    @Column(name = "project_id")
    Integer projectId;
    @Column(name = "creator_id")
    Integer creatorId;
    @Column(name = "created_at")
    LocalDate createdAt;
    @Column(name = "assigned_id")
    Integer assignedId;
    @Column(name = "qa_id")
    Integer qaId;
    String status;
    @Column(name = "estimated_start")
    LocalDateTime estimatedStart;
    @Column(name = "estimated_finish")
    LocalDateTime estimatedFinish;
    @Column(name = "expected_finish")
    LocalDateTime expectedFinish;
    String cause;
    String solution;

}
