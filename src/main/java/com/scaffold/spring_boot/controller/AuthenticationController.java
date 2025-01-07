package com.scaffold.spring_boot.controller;

import com.scaffold.spring_boot.dto.response.ApiResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.scaffold.spring_boot.dto.request.*;
import com.scaffold.spring_boot.dto.response.AuthenticationResponse;
import com.scaffold.spring_boot.dto.response.IntrospectResponse;
import com.scaffold.spring_boot.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticateUser(authenticationRequest))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticateJwt(
            @RequestHeader("Authorization") @Valid IntrospectRequest request) {
        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> authenticateJwt(@RequestBody @Valid LogoutRequest request) {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshAuthentication(@RequestBody @Valid RefreshRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.refreshToken(request))
                .build();
    }
}
