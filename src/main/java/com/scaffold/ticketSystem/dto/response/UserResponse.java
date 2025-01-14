package com.scaffold.ticketSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    String id;
    String username;
    Integer unitId;
    String email;
    String role;
    Boolean locked;
}
