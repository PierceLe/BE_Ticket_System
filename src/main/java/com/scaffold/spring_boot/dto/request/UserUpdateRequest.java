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
    private String password;
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    private LocalDate dob;
}
