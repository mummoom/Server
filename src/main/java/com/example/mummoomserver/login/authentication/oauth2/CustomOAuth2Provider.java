package com.example.mummoomserver.login.authentication.oauth2;

public enum CustomOAuth2Provider {
    GOOGLE, KAKAO;

    public ClientRegistration.ClientRegistrationBuilder getBuilder(String registrationId) {
        return ClientRegistration.builder().registrationId(registrationId);
    }
}