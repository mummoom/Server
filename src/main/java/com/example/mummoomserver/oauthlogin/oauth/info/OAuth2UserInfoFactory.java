package com.example.mummoomserver.oauthlogin.oauth.info;

import com.example.mummoomserver.oauthlogin.oauth.entity.ProviderType;
import com.example.mummoomserver.oauthlogin.oauth.info.impl.GoogleOAuth2UserInfo;
import com.example.mummoomserver.oauthlogin.oauth.info.impl.KakaoOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
