package com.scaffold.spring_boot.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.scaffold.spring_boot.enums.Cause;
import com.scaffold.spring_boot.enums.Status;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "project_id")
    Integer projectId;

    @Column(name = "creator_id")
    String creatorId;

    @Column(name = "created_at")
    LocalDate createdAt;

    @Column(name = "assigned_id")
    String assignedId;

    @Column(name = "qa_id")
    String qaId;

    Status status;

    @Column(name = "estimated_start")
    LocalDateTime estimatedStart;

    @Column(name = "estimated_finish")
    LocalDateTime estimatedFinish;

    @Column(name = "expected_finish")
    LocalDateTime expectedFinish;

    Cause cause;
    String solution;

    @Column(name = "qa_opinion")
    String qaOpinion;

    @Column(name = "cause_details")
    String causeDetails;

    String description;

    @Column(name = "attached_file")
    String attachedFile;

    @Column(name = "assigned_note")
    String assignedNote;

    @Column(name = "rejected_reason")
    String rejectedReason;
}
