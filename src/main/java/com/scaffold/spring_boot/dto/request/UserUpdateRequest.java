package com.scaffold.spring_boot.dto.request;

import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @Nullable
    String avatarUrl;
    @Nullable
    String description;
    @Nullable
    String fullName;
    @Nullable
    LocalDate dob;
    @Nullable
    String email;
}
