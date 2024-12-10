package com.scaffold.spring_boot.dto.request;

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
    @Size(min = 3, message = "USER_NAME_NOT_VALID")
    String username;

    String lastName;
    @Size(min = 8, message = "USER_PASSWORD_NOT_VALID")
    String password;
    String firstName;

    LocalDate dob;
}
