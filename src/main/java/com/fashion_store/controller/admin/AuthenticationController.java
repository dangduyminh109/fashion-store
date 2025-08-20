package com.fashion_store.controller.admin;

import com.fashion_store.dto.auth.request.AuthenticationRequest;
import com.fashion_store.dto.auth.request.LogoutRequest;
import com.fashion_store.dto.auth.request.RefreshRequest;
import com.fashion_store.dto.auth.response.AuthenticationResponse;
import com.fashion_store.dto.common.response.ApiResponse;
import com.fashion_store.dto.user.response.UserResponse;
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
@RequestMapping("/api/admin/auth")
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
