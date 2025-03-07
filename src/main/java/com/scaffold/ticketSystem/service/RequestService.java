package com.scaffold.ticketSystem.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.scaffold.ticketSystem.dto.request.SocketMessage;
import com.scaffold.ticketSystem.enums.MessageType;
import com.scaffold.ticketSystem.repository.dao.LeastBusyQA_DAO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scaffold.ticketSystem.dto.request.request_ticket.RequestCreationRequest;
import com.scaffold.ticketSystem.dto.response.*;
import com.scaffold.ticketSystem.entity.Request;
import com.scaffold.ticketSystem.entity.Users;
import com.scaffold.ticketSystem.enums.Status;
import com.scaffold.ticketSystem.exception.AppException;
import com.scaffold.ticketSystem.exception.ErrorCode;
import com.scaffold.ticketSystem.repository.ProjectRepository;
import com.scaffold.ticketSystem.repository.RequestRepository;
import com.scaffold.ticketSystem.repository.UserRepository;
import com.scaffold.ticketSystem.utils.FileUtils;
import com.scaffold.ticketSystem.utils.SortUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
public class RequestService {
    @NonFinal
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/attachedFile/";

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final ProjectService projectService;
    private final UserService userService;
    private final SocketService socketService;
    private final ModelMapper modelMapper;
    private final FileUtils fileUtils;
    private final SortUtils sortUtils;

    public RequestCreationResponse createRequest(RequestCreationRequest requestCreationRequest, MultipartFile attachedFile) {
        if (!projectRepository.existsById(requestCreationRequest.getProjectId())) {
            throw new AppException(ErrorCode.PROJECT_ID_NOT_EXISTED);
        }

        String creatorId = SecurityContextHolder.getContext().getAuthentication().getName();
        Request request = Request.builder()
                .projectId(requestCreationRequest.getProjectId())
                .creatorId(creatorId)
                .createdAt(LocalDate.now())
                .status(Status.PENDING)
                .expectedFinish(requestCreationRequest.getExpectedFinish())
                .description(requestCreationRequest.getDescription())
                .build();

        if (Objects.isNull(attachedFile) || attachedFile.isEmpty()) {
        } else if (!isValidAttachmentType(attachedFile.getContentType())) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        } else {
            String filePath =
                    FILE_PATH + requestCreationRequest.getProjectId() + "_" + attachedFile.getOriginalFilename();
            request.setAttachedFile(fileUtils.saveFile(filePath, attachedFile));
        }

        LeastBusyQA_DAO assignedQA = userRepository.findLeastBusyQA().getFirst();
        request.setQaId(assignedQA.getId());
        requestRepository.save(request);
        SocketMessage socketMessage = SocketMessage.builder()
                .type(MessageType.CHAT)
                .senderId(creatorId)
                .receiverId(assignedQA.getId())
                .content("You have been assigned to proceeded a new ticket")
                .build();
        socketService.sendNotification(socketMessage);

        return RequestCreationResponse.builder()
                .project(projectService.getProjectById(request.getProjectId()))
                .creator(userService.getMyInfo())
                .createdAt(request.getCreatedAt())
                .qaUser(modelMapper.map(assignedQA, UserResponse.class))
                .status(request.getStatus())
                .expectedFinish(request.getExpectedFinish())
                .description(request.getDescription())
                .attachedFile(request.getAttachedFile())
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

    public RequestResponse getRequestById(Integer id) {
        Request request = requestRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ID_NOT_FOUND));
        return modelMapper.map(request, RequestResponse.class);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('QA')")
    public PageResponse<Request> getRequestsFilter(
            Integer projectId,
            String creatorId,
            String qaId,
            Status status,
            String assignedId,
            Integer page,
            Integer size,
            String sort) {
        List<Sort.Order> orders = sortUtils.generateOrder(sort);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orders));

        var pageData = requestRepository.findRequestsByFilters(projectId, creatorId, qaId, status, assignedId, pageable);

        return PageResponse.<Request>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent())
                .build();
    }
}
