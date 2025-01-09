package com.scaffold.spring_boot.dto.request.request_ticket;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreationRequest {
    @NotNull(message = "PROJECT_ID_NOT_EMPTY")
    Integer projectId;
    @NotNull(message = "DESCRIPTION_NOT_EMPTY")
    String descriptions;
    @NotNull(message = "EXPECTED_TIME_NOT_EMPTY")
    LocalDateTime expectedFinish;

}
