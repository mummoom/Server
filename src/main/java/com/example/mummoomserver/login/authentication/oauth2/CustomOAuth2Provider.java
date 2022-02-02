package com.example.mummoomserver.login.authentication.oauth2;

import org.springframework.security.oauth2.client.registration.ClientRegistration;

public enum CustomOAuth2Provider {
    GOOGLE, KAKAO;

    public ClientRegistration.ClientRegistrationBuilder getBuilder(String registrationId) {
        return ClientRegistration.builder().registrationId(registrationId);
    }
}