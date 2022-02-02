package com.example.mummoomserver.login.authentication.oauth2.userInfo;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    protected OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getUserIdx();

    public abstract String getNickName();

    public abstract String getEmail();
}
