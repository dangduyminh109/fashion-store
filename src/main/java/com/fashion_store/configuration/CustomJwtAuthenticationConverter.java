package com.fashion_store.configuration;

import com.fashion_store.entity.User;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String username = jwt.getSubject();
        if (username == null) throw new AppException(ErrorCode.INVALID_TOKEN);

        User user = userRepository.findByUsernameFetchPermissions(username)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));

        Set<GrantedAuthority> authorities = user.getRole().getPermissions()
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getCode()))
                .collect(Collectors.toSet());
        return new UsernamePasswordAuthenticationToken(user, jwt, authorities);
    }
}
