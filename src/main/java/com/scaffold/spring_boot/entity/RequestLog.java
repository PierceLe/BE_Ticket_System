package com.scaffold.spring_boot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "requests_log")
public class RequestLog {
    @Id
    Integer id;
    @Column(name = "request_id")
    Integer requestId;
    String status;
    @Column(name = "created_at")
    LocalDateTime createAt;
    @Column(name = "user_id")
    Integer userId;
}
