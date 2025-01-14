package com.scaffold.ticketSystem.dto.request.user;

import java.time.LocalDate;

import jakarta.annotation.Nullable;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @Nullable
    String description;

    @Nullable
    String fullName;

    @Nullable
    LocalDate dob;

    @Nullable
    String email;
}
