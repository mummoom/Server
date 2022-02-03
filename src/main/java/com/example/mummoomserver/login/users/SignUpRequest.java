package com.example.mummoomserver.login.users;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// 회원가입시 요청되는 데이터가 적절한 지 검사

@Getter
@NoArgsConstructor
public class SignUpRequest {
    @Size(min = 1, max = 20, message = "이름이 입력되지 않았거나 너무 긴 이름입니다.")
    private String nickName;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;

    @Pattern(regexp = "[a-zA-Z!@#$%^&*-_]{6,20}", message = "6~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다.")
    private String password;

    private String imgUrl;

    @Builder
    public SignUpRequest(String nickName, String email, String password,String imgUrl) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.imgUrl =  imgUrl;
    }
}
