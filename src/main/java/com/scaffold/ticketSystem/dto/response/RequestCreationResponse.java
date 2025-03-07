package com.scaffold.ticketSystem.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.scaffold.ticketSystem.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreationResponse {
    ProjectResponse project;
    UserResponse creator;
    LocalDate createdAt;
    UserResponse assignedUser;
    UserResponse qaUser;
    Status status;
    LocalDateTime estimatedStart;
    LocalDateTime estimatedFinish;
    LocalDateTime expectedFinish;
    String cause;
    String solution;
    String qaOpinion;
    String causeDetails;
    String description;
    String attachedFile;
    String assignedNote;
    String rejectedReason;
}
