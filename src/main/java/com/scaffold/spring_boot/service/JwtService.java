package com.scaffold.spring_boot.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;
import com.scaffold.spring_boot.repository.InvalidatedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    public String generateToken(String userId, String role) {
        try {
            // Create JWT Header
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

            // Add Claims
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(userId)
                    .issuer("hale0087@uni.sydney.edu.au")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(48, ChronoUnit.HOURS)))
                    .claim("scope", role)
                    .jwtID(UUID.randomUUID().toString())
                    .build();

            // Sign the token
            SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
            signedJWT.sign(new MACSigner(SIGNER_KEY.getBytes()));

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.TOKEN_GENERATION_FAILED);
        }
    }

    public boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            return signedJWT.verify(verifier) && expiryTime.after(new Date())
                    && !invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID());
        } catch (JOSEException | ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public JWTClaimsSet parseTokenClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}