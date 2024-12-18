package com.scaffold.spring_boot.dto.request;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntrospectRequest {
    @NotNull
    String token;
}
