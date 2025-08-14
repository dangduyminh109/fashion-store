package com.fashion_store.configuration;

import com.fashion_store.Utils.JwtUtils;
import com.fashion_store.enums.TypeUser;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String jwt = null;
        try {
            jwt = jwtUtils.generateToken(oAuth2User.getEmail(), TypeUser.CUSTOMER.name());
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("http://localhost:5173?token=" + jwt);
    }
}
