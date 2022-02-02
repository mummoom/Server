package com.example.mummoomserver.login.authentication.oauth2.userInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUserIdx() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getNickName() {
        return (String) parsingProfile().get("nickName");
    }

    @Override
    public String getEmail() {
        return (String) parsingProperties().get("email");
    }

    private Map<String, Object> parsingProperties() {
        return (Map<String, Object>) attributes.get("kakao_account");
    }

    private Map<String, Object> parsingProfile() {
        return (Map<String, Object>)parsingProperties().get("profile");
    }
}
