package com.fashion_store.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ROUTER = {
            "/admin/auth/login",
            "/admin/auth/logout",
            "/admin/auth/refresh",
            "/auth/login",
            "/auth/logout",
            "/auth/refresh",
            "/auth/register",
            "/auth/send-otp",
            "/auth/oauth2/success",
            "/oauth2/**",
            "/auth/**",
            "/order/client/create"
    };

    @Value("${jwt.signerKey}")
    protected String signerKey;
    @Autowired
    private CustomJwtAuthenticationConverter customJwtAuthenticationConverter;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Autowired
    CustomJwtDecoder customJwtDecoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers(PUBLIC_ROUTER).permitAll()
                                .anyRequest().authenticated()
                )
                .with(new OAuth2LoginConfigurer<HttpSecurity>(), oauth2 -> oauth2
                        .successHandler(oAuth2SuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .failureHandler((request, response, exception) -> {
                            if (exception.getMessage().contains("access_denied")) {
                                response.sendRedirect("/login?error=cancelled");
                            } else {
                                response.sendRedirect("/login?error=oauth2_error");
                            }
                        })
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(jwtConfigurer ->
                                jwtConfigurer.decoder(customJwtDecoder)
                                        .jwtAuthenticationConverter(customJwtAuthenticationConverter)
                        )
                        .authenticationEntryPoint(new jwtAuthenticationEntryPoint())
                );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
