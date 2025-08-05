package com.fashion_store.controller;

import com.fashion_store.dto.request.*;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.AuthenticationResponse;
import com.fashion_store.dto.response.CustomerResponse;
import com.fashion_store.service.CustomerAuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerAuthController {
    CustomerAuthService customerAuthService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody @Valid CustomerAuthRequest authenticationRequest) throws JOSEException {
        AuthenticationResponse authenticationResponse = customerAuthService.login(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationResponse)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        customerAuthService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        AuthenticationResponse result = customerAuthService.refresh(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/register")
    ApiResponse<CustomerResponse> register(@RequestBody CustomerRegisterRequest request) throws JOSEException {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerAuthService.register(request))
                .build();
    }

    @PostMapping("/send-otp")
    ApiResponse<Void> sendOtp(@RequestBody SendOtpRequest request) throws MessagingException {
        return ApiResponse.<Void>builder()
                .message(customerAuthService.sendOtp(request))
                .build();
    }
}
