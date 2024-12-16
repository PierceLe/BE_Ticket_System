package com.scaffold.spring_boot.controller;

import com.scaffold.spring_boot.dto.request.ApiResponse;
import com.scaffold.spring_boot.dto.request.request_ticket.RequestCreationRequest;
import com.scaffold.spring_boot.dto.response.RequestResponse;
import com.scaffold.spring_boot.service.RequestService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public ApiResponse<RequestResponse> requestCreation(@RequestBody @NonNull RequestCreationRequest request) {
        return ApiResponse.<RequestResponse>builder()
                .code(200)
                .build();
    }
}
