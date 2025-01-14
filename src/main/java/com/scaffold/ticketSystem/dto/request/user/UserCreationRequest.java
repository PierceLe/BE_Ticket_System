package com.scaffold.ticketSystem.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotNull(message = "USERNAME_NOT_EMPTY")
    @Size(min = 4, message = "USER_NAME_NOT_VALID")
    String username;

    @NotNull(message = "PASSWORD_NOT_EMPTY")
    @Size(min = 8, message = "USER_PASSWORD_NOT_VALID")
    String password;

    @NotNull(message = "UNIT_ID_NOT_EMPTY")
    Integer unitId;

    @NotNull(message = "EMAIL_NOT_EMPTY")
    @Email(message = "EMAIL_NOT_VALID")
    String email;

    @NotNull(message = "ROLE_ID_NOT_EMPTY")
    String role;
}
