package com.scaffold.spring_boot.controller;

import com.scaffold.spring_boot.dto.response.PageResponse;
import com.scaffold.spring_boot.dto.response.RequestResponse;
import com.scaffold.spring_boot.enums.Status;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.scaffold.spring_boot.dto.request.request_ticket.RequestCreationRequest;
import com.scaffold.spring_boot.dto.response.ApiResponse;
import com.scaffold.spring_boot.dto.response.RequestCreationResponse;
import com.scaffold.spring_boot.service.RequestService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public ApiResponse<RequestCreationResponse> requestCreation(
            @RequestBody @Valid RequestCreationRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        return ApiResponse.<RequestCreationResponse>builder()
                .code(200)
                .result(requestService.createRequest(request, file))
                .build();
    }

    @GetMapping()
    public ApiResponse<PageResponse<RequestResponse>> getSearchFilter(
            @RequestParam(value = "projectId", required = false) Integer projectId,
            @RequestParam(value = "creatorId", required = false) String creatorId,
            @RequestParam(value = "qaId", required = false) String qaId,
            @RequestParam(value = "status", required = false) Status status,
            @RequestParam(value = "assignedId", required = false) String assignedId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "createdAt,asc") String sort) {

        ApiResponse<PageResponse<RequestResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(requestService.getRequestsFilter(projectId, creatorId, qaId, status, assignedId, page, size, sort));
        return apiResponse;
    }
}
