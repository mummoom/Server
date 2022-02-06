package com.example.mummoomserver.login.users.requestResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.*;

// 회원가입시 요청되는 데이터가 적절한 지 검사

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //null 이면 생성되지 않음
public class SignUpRequest {

    @Size(min = 5, max = 20, message = "이름이 입력되지 않았거나 너무 긴 이름입니다.")
    private String nickName;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;

    @Pattern(regexp = "[a-zA-Z!@#$%^&*-_]{6,20}", message = "6~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다.")
    private String password;



    @Builder
    public SignUpRequest(String nickName, String email, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;

    }
}
