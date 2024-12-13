package com.scaffold.spring_boot.dto.response;

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
