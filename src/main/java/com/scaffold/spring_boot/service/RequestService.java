package com.scaffold.spring_boot.service;

import com.scaffold.spring_boot.dto.request.request_ticket.RequestCreationRequest;
import com.scaffold.spring_boot.dto.response.RequestResponse;
import com.scaffold.spring_boot.entity.Request;
import com.scaffold.spring_boot.enums.Status;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.repository.ProjectRepository;
import com.scaffold.spring_boot.repository.UserRepository;
import com.scaffold.spring_boot.utils.FileUtils;
import lombok.experimental.NonFinal;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.scaffold.spring_boot.repository.RequestRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestService {
    @NonFinal
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/attachedFile/";
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
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

        if (Objects.isNull(attachedFile) || attachedFile.isEmpty()) {}
        else if (!isValidAttachmentType(attachedFile.getContentType())) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }
        String filePath = FILE_PATH + requestCreationRequest.getProjectId() + "_" + attachedFile.getOriginalFilename();
        request.setAttachedFile(fileUtils.saveFile(filePath, attachedFile));
        return modelMapper.map(requestRepository.save(request), RequestResponse.class);
    }


    private boolean isValidAttachmentType(String fileType) {
        return fileType.equalsIgnoreCase("image/jpeg") // For jpg and jpeg
                || fileType.equalsIgnoreCase("image/png")
                || fileType.equalsIgnoreCase("image/svg+xml")
                || fileType.equalsIgnoreCase("application/pdf") // For PDF
                || fileType.equalsIgnoreCase("application/msword") // For DOC
                || fileType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document") // For DOCX
                || fileType.equalsIgnoreCase("text/plain"); // For TXT
    }

}
