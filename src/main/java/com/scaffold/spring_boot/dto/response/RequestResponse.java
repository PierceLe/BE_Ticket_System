package com.scaffold.spring_boot.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.scaffold.spring_boot.enums.Cause;
import com.scaffold.spring_boot.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestResponse {
    private Integer projectId;
    private String creatorId;
    private LocalDate createdAt;
    private String qaId;
    private String assignedId;
    private Status status;
    private LocalDateTime estimatedStart;
    private LocalDateTime estimatedFinish;
    private LocalDateTime expectedFinish;
    private Cause cause;
    private String solution;
    private String qaOpinion;
    private String causeDetails;
    private String description;
    private String attachedFile;
    private String assignedNote;
    private String rejectedReason;
}
