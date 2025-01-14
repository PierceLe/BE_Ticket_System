package com.scaffold.ticketSystem.dto.request.request_ticket;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreationRequest {
    @NotNull(message = "PROJECT_ID_NOT_EMPTY")
    Integer projectId;

    @NotNull(message = "DESCRIPTION_NOT_EMPTY")
    String description;

    @NotNull(message = "EXPECTED_TIME_NOT_EMPTY")
    LocalDateTime expectedFinish;
}
