package com.scaffold.spring_boot.controller;

import com.nimbusds.jose.JOSEException;
import com.scaffold.spring_boot.dto.request.ApiResponse;
import com.scaffold.spring_boot.dto.request.AuthenticationRequest;
import com.scaffold.spring_boot.dto.request.IntrospectRequest;
import com.scaffold.spring_boot.dto.response.AuthenticationResponse;
import com.scaffold.spring_boot.dto.response.IntrospectResponse;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ApiResponse.<AuthenticationResponse>builder().
                result(authenticationService.authenticateUser(authenticationRequest))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticateJwt(@RequestHeader("Authorization") @Valid IntrospectRequest request) {
        try {
            return ApiResponse.<IntrospectResponse>builder()
                    .result(authenticationService.introspect(request))
                    .build();
        } catch (JOSEException | ParseException e) {
            throw new AppException(ErrorCode.INVALID_JWT);
        }
    }
}
