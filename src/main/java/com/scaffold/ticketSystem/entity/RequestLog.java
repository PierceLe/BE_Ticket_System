package com.scaffold.ticketSystem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "requests_log")
public class RequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "request_id")
    Integer requestId;

    String status;

    @Column(name = "created_at")
    LocalDateTime createAt;

    @Column(name = "user_id")
    Integer userId;
}
