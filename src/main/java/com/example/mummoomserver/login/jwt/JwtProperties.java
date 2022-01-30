package com.example.mummoomserver.login.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 시크릿 키 등을 등록한 것들을 읽어 저장

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    private SignatureAlgorithm signatureAlgorithm;
    private Long tokenExpired;
}
