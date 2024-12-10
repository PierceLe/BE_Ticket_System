package com.scaffold.spring_boot.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4, message = "USER_NAME_NOT_VALID")
    String username;
    @Size(min = 8, message = "USER_PASSWORD_NOT_VALID")
    String password;
    Integer unitId;
    @Email(message = "EMAIL_NOT_VALID")
    String email;
    String role;

}
