package com.fashion_store.controller;

import com.fashion_store.dto.request.AuthenticationRequest;
import com.fashion_store.dto.request.LogoutRequest;
import com.fashion_store.dto.request.RefreshRequest;
import com.fashion_store.dto.response.*;
import com.fashion_store.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws JOSEException {
        UserResponse authenticationResponse = authenticationService.login(authenticationRequest);
        return ApiResponse.<UserResponse>builder()
                .result(authenticationResponse)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        AuthenticationResponse result = authenticationService.refresh(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
}
