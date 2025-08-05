package com.fashion_store.service;

import com.fashion_store.dto.request.AuthenticationRequest;
import com.fashion_store.dto.request.LogoutRequest;
import com.fashion_store.dto.request.RefreshRequest;
import com.fashion_store.dto.response.AuthenticationResponse;
import com.fashion_store.entity.InvalidatedToken;
import com.fashion_store.entity.User;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.repository.InvalidatedTokenRepository;
import com.fashion_store.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESH_DURATION;

    public AuthenticationResponse login(AuthenticationRequest request) throws JOSEException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        String token = request.getToken();
        SignedJWT signedJWT = verifyToken(token, true);
        String jti = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var newInvalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(newInvalidatedToken);
    }

    public AuthenticationResponse refresh(RefreshRequest request) throws ParseException, JOSEException {
        String token = request.getToken();
        SignedJWT signedJWT = verifyToken(token, true);
        String jti = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var newInvalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(newInvalidatedToken);

        String username = signedJWT.getJWTClaimsSet().getSubject();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.NOT_EXIST)
        );
        String newToken = generateToken(user);
        return AuthenticationResponse.builder()
                .token(newToken)
                .build();
    }

    String generateToken(User user) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("fashion-store")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(new MACSigner(SIGNER_KEY));
        return signedJWT.serialize();
    }

    public boolean introspect(String token) {
        boolean valid = true;
        try {
            verifyToken(token, false);
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(jwsVerifier);

        Date expiryTime = isRefresh
                ? new Date(signedJWT.getJWTClaimsSet().getExpirationTime().toInstant().plus(REFRESH_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        if (!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        String jti = signedJWT.getJWTClaimsSet().getJWTID();
        if (invalidatedTokenRepository.existsById(jti))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }
}
