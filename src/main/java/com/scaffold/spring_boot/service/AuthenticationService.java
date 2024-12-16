package com.scaffold.spring_boot.service;

import com.scaffold.spring_boot.dto.request.AuthenticationRequest;
import com.scaffold.spring_boot.dto.request.IntrospectRequest;
import com.scaffold.spring_boot.dto.response.AuthenticationResponse;
import com.scaffold.spring_boot.dto.response.IntrospectResponse;
import com.scaffold.spring_boot.entity.Users;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BruteForceProtectionService bruteForceProtectionService;

    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        // Check if user is blocked
        if (bruteForceProtectionService.isBlocked(username)) {
            throw new AppException(ErrorCode.ACCOUNT_LOCKED);
        }
        Users user = userRepository.findByUsername(authenticationRequest.getUsername());
        if (Objects.isNull(user)) {
            bruteForceProtectionService.loginFailed(username);
            throw new AppException(ErrorCode.AUTHENTICATION_FAILED);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            bruteForceProtectionService.loginFailed(username);
            throw new AppException(ErrorCode.AUTHENTICATION_FAILED);
        }

        bruteForceProtectionService.loginSucceeded(username);
        // Generate token using JwtService
        String token = jwtService.generateToken(user.getId(), user.getRole());

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {
        String bearerToken = introspectRequest.getToken();

        // Validate Bearer token format
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        String token = bearerToken.substring(7); // Remove "Bearer " prefix

        boolean isValid = jwtService.verifyToken(token);

        return IntrospectResponse.builder()
                .isValid(isValid)
                .build();
    }
}