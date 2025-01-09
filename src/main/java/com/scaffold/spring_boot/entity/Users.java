package com.scaffold.spring_boot.entity;

import java.time.LocalDate;
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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String username;
    String role;

    @Column(name = "unit_id")
    Integer unitId;

    @Column(name = "created_at")
    LocalDate createdAt;

    String email;

    @Column(name = "avatar_url")
    String avatarUrl;

    String password;
    String description;

    @Column(name = "full_name")
    String fullName;

    LocalDate dob;
    Boolean locked;
    Integer activeTickets;
    LocalDateTime recentResolvedTicket;
}
