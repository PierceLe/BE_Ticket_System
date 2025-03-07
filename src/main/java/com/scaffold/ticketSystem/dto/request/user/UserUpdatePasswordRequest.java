package com.scaffold.ticketSystem.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
