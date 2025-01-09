package com.scaffold.spring_boot.controller;

import org.springframework.web.bind.annotation.*;

import com.scaffold.spring_boot.dto.response.ApiResponse;
import com.scaffold.spring_boot.dto.request.request_ticket.RequestCreationRequest;
import com.scaffold.spring_boot.dto.response.RequestResponse;
import com.scaffold.spring_boot.service.RequestService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public ApiResponse<RequestResponse> requestCreation(
            @RequestBody @NonNull RequestCreationRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file
            ) {
        return ApiResponse.<RequestResponse>builder()
                .code(200)
                .result(requestService.createRequest(request, file))
                .build();
    }
}
