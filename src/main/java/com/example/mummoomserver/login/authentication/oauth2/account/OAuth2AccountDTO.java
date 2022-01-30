package com.example.mummoomserver.login.authentication.oauth2.account;

import com.example.mummoomserver.login.authentication.oauth2.OAuth2Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OAuth2AccountDTO {
    private String provider;
    private String providerId;
    private LocalDateTime createdAt;
    private OAuth2Token oAuth2Token;

    @Builder
    public OAuth2AccountDTO(String provider, String providerId, String token, String refreshToken, LocalDateTime createdAt, LocalDateTime tokenExpiredAt) {
        this.provider = provider;
        this.providerId = providerId;
        this.createdAt = createdAt;
        this.oAuth2Token = new OAuth2Token(token, refreshToken, tokenExpiredAt);
    }
}
