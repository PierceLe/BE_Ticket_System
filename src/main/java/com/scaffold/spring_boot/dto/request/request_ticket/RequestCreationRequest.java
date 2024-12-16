package com.scaffold.spring_boot.dto.request.request_ticket;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreationRequest {
    String title;
    Integer projectId;
    String cause;
    String causeDetails;
    String descriptions;
    String attachedFile;

}
