package com.example.mummoomserver.login.service;

import com.example.mummoomserver.login.users.Role;
import com.example.mummoomserver.login.users.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickName;
    private String email;
    private String imgUrl;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickName, String email, String imgUrl) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickName = nickName;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("kakao".equals(registrationId)){
            return ofKakao("nickName", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickName((String) attributes.get("name")) //name? nickname?
                .email((String) attributes.get("email")) // imgurl? picture
                .imgUrl((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> response = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");
        return OAuthAttributes.builder()
                .nickName((String)profile.get("nickname"))
                .email((String)response.get("email"))
                .imgUrl((String)profile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .nickName(nickName)
                .email(email)
                .imgUrl(imgUrl)
                .role(Role.GUEST)
                .build();
    }




}
