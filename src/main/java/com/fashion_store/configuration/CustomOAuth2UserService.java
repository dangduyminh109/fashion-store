package com.fashion_store.configuration;

import com.fashion_store.entity.Customer;
import com.fashion_store.enums.AuthProvider;
import com.fashion_store.repository.CustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    CustomerRepository customerRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String googleId = oAuth2User.getAttribute("sub");

        Customer customer = customerRepository.findByEmail(email)
                .orElseGet(() -> {
                    Customer newCustomer = Customer.builder()
                            .email(email)
                            .fullName(name)
                            .status(true)
                            .authProvider(AuthProvider.GOOGLE)
                            .providerId(googleId)
                            .build();
                    return customerRepository.save(newCustomer);
                });
        return new CustomOAuth2User(oAuth2User.getAttributes());
    }
}
