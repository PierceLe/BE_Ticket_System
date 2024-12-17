package com.scaffold.spring_boot.service;

import com.nimbusds.jwt.SignedJWT;
import com.scaffold.spring_boot.dto.request.AuthenticationRequest;
import com.scaffold.spring_boot.dto.request.IntrospectRequest;
import com.scaffold.spring_boot.dto.request.LogoutRequest;
import com.scaffold.spring_boot.dto.request.RefreshRequest;
import com.scaffold.spring_boot.dto.response.AuthenticationResponse;
import com.scaffold.spring_boot.dto.response.IntrospectResponse;
import com.scaffold.spring_boot.entity.InvalidatedToken;
import com.scaffold.spring_boot.entity.Users;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.repository.InvalidatedTokenRepository;
import com.scaffold.spring_boot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

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

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {
        var token = introspectRequest.getToken();
        boolean isValid = true;
        try {
            if (!jwtService.verifyToken(token))
                isValid = false;
        }
        catch (AppException e) {
            log.error(e.getMessage());
            isValid = false;
        }
        return IntrospectResponse.builder().isValid(isValid).build();
    }

    public void logout(LogoutRequest request) {
        if (jwtService.verifyToken(request.getToken())) {
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
            }


        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) {
        // - check the expiration date of token
        if (jwtService.verifyToken(request.getToken())) {
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
                var user = userRepository.findById(userId)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

                var token = jwtService.generateToken(userId, user.getRole());
                return AuthenticationResponse.builder()
                        .token(token)
                        .build();

            } catch (ParseException e) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
        }
        throw new AppException(ErrorCode.INVALID_TOKEN);
    }

}