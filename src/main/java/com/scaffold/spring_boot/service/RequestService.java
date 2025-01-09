package com.scaffold.spring_boot.service;

import java.time.LocalDate;
import java.util.Objects;

import com.scaffold.spring_boot.dto.response.UserResponse;
import com.scaffold.spring_boot.entity.Users;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scaffold.spring_boot.dto.request.request_ticket.RequestCreationRequest;
import com.scaffold.spring_boot.dto.response.RequestResponse;
import com.scaffold.spring_boot.entity.Request;
import com.scaffold.spring_boot.enums.Status;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.repository.ProjectRepository;
import com.scaffold.spring_boot.repository.RequestRepository;
import com.scaffold.spring_boot.repository.UserRepository;
import com.scaffold.spring_boot.utils.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
public class RequestService {
    @NonFinal
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/attachedFile/";

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RequestRepository requestRepository;
    private final ModelMapper modelMapper;
    private final FileUtils fileUtils;

    public RequestResponse createRequest(RequestCreationRequest requestCreationRequest, MultipartFile attachedFile) {
        if (!projectRepository.existsById(requestCreationRequest.getProjectId())) {
            throw new AppException(ErrorCode.PROJECT_ID_NOT_EXISTED);
        }
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Request request = Request.builder()
                .projectId(requestCreationRequest.getProjectId())
                .creatorId(id)
                .createdAt(LocalDate.now())
                .qaId(null) //
                .status(Status.PENDING)
                .expectedFinish(requestCreationRequest.getExpectedFinish())
                .description(requestCreationRequest.getDescriptions())
                .build();

        if (Objects.isNull(attachedFile) || attachedFile.isEmpty()) {
        } else if (!isValidAttachmentType(attachedFile.getContentType())) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        } else {
            String filePath =
                    FILE_PATH + requestCreationRequest.getProjectId() + "_" + attachedFile.getOriginalFilename();
            request.setAttachedFile(fileUtils.saveFile(filePath, attachedFile));
        }
        return RequestResponse.builder()
                .project(projectService.getProjectById(request.getProjectId()))
                .creator(userService.getMyInfo())
                .createdAt(request.getCreatedAt())
                .assignedUser(null)
                .qaUser(modelMapper.map(userRepository.findLeastBusyQA(), UserResponse.class))
                .status(request.getStatus())
                .estimatedStart(null)
                .estimatedFinish(null)
                .expectedFinish(request.getExpectedFinish())
                .cause(null)
                .solution(null)
                .qaOpinion(null)
                .causeDetails(null)
                .description(request.getDescription())
                .attachedFile(request.getAttachedFile())
                .assignedNote(null)
                .rejectedReason(null)
                .build();
    }

    private boolean isValidAttachmentType(String fileType) {
        return fileType.equalsIgnoreCase("image/jpeg") // For jpg and jpeg
                || fileType.equalsIgnoreCase("image/png")
                || fileType.equalsIgnoreCase("image/svg+xml")
                || fileType.equalsIgnoreCase("application/pdf") // For PDF
                || fileType.equalsIgnoreCase("application/msword") // For DOC
                || fileType.equalsIgnoreCase(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document") // For DOCX
                || fileType.equalsIgnoreCase("text/plain"); // For TXT
    }
}
