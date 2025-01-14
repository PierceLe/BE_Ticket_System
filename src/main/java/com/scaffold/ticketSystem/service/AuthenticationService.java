package com.scaffold.ticketSystem.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jwt.SignedJWT;
import com.scaffold.ticketSystem.dto.request.AuthenticationRequest;
import com.scaffold.ticketSystem.dto.request.IntrospectRequest;
import com.scaffold.ticketSystem.dto.request.LogoutRequest;
import com.scaffold.ticketSystem.dto.request.RefreshRequest;
import com.scaffold.ticketSystem.dto.response.AuthenticationResponse;
import com.scaffold.ticketSystem.dto.response.IntrospectResponse;
import com.scaffold.ticketSystem.entity.InvalidatedToken;
import com.scaffold.ticketSystem.entity.Users;
import com.scaffold.ticketSystem.exception.AppException;
import com.scaffold.ticketSystem.exception.ErrorCode;
import com.scaffold.ticketSystem.repository.InvalidatedTokenRepository;
import com.scaffold.ticketSystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BruteForceProtectionService bruteForceProtectionService;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

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

        return AuthenticationResponse.builder().token(token).build();
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {
        var token = introspectRequest.getToken();
        boolean isValid = true;
        try {
            if (!jwtService.verifyToken(token, false)) isValid = false;
        } catch (AppException e) {
            log.error(e.getMessage());
            isValid = false;
        }
        return IntrospectResponse.builder().isValid(isValid).build();
    }

    public void logout(LogoutRequest request) {
        if (jwtService.verifyToken(request.getToken(), true)) {
            try {
                var signedToken = SignedJWT.parse(request.getToken().substring(7));
                String jit = signedToken.getJWTClaimsSet().getJWTID();
                Date expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();
                InvalidatedToken token = InvalidatedToken.builder()
                        .id(jit)
                        .expiryTime(expiryTime)
                        .build();
                invalidatedTokenRepository.save(token);
            } catch (ParseException e) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            } catch (AppException e) {
                log.error(e.getMessage());
            }
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) {
        // - check the expiration date of token
        if (jwtService.verifyToken(request.getToken(), true)) {
            try {
                var signedJWT = SignedJWT.parse(request.getToken().substring(7));
                var jit = signedJWT.getJWTClaimsSet().getJWTID();
                var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
                InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                        .id(jit)
                        .expiryTime(expiryTime)
                        .build();
                invalidatedTokenRepository.save(invalidatedToken);
                var userId = signedJWT.getJWTClaimsSet().getSubject();
                var user =
                        userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

                var token = jwtService.generateToken(userId, user.getRole());
                return AuthenticationResponse.builder().token(token).build();

            } catch (ParseException e) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
        }
        throw new AppException(ErrorCode.INVALID_TOKEN);
    }
}
