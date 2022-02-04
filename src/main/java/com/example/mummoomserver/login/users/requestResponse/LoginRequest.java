package com.example.mummoomserver.login.users.requestResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "이메일을 입력하세요.")
    private String email;
    @NotBlank(message = "패스워드를 입력하세요.")
    private String password;
    @Builder
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}