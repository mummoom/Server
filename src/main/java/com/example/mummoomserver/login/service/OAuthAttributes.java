package com.example.mummoomserver.login.service;

import com.example.mummoomserver.login.users.Role;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String name;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        } else{
        return ofGoogle(userNameAttributeName, attributes);
        }
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> response = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");
        return OAuthAttributes.builder()
                .name((String)profile.get("nickname"))
                .email((String)response.get("email"))
                .picture((String)profile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    UUID garbagePassword =  UUID.randomUUID();
    int ran = (int)( Math.random() * 100 );

    public User toEntity() {
        return User.builder()
                .nickName(name+ran)
                .email(email)
                .password(garbagePassword.toString())
                .imgUrl(picture)
                .role(Role.USER)
                .type(UserType.OAUTH)
                .build();
    }



}
