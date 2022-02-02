package com.example.mummoomserver.login.authentication.oauth2.userInfo;

import java.util.Map;

public class DefaultOAuth2UserInfo  extends OAuth2UserInfo {

    public DefaultOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUserIdx() {
        return (String) attributes.get("userIdx");
    }

    @Override
    public String getNickName() {
        return (String) attributes.get("nickName");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}

