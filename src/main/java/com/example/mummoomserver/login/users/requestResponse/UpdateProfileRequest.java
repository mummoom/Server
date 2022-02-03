package com.example.mummoomserver.login.users.requestResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UpdateProfileRequest {
    @Size(min = 1, max = 20, message = "이름이 입력되지 않았거나 너무 긴 이름입니다.")
    private String nickName;
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;

    private String imgUrl;

    @Builder
    public UpdateProfileRequest(String nickName, String email, String imgUrl) {
        this.nickName = nickName;
        this.email = email;
        this.imgUrl = imgUrl;
    }
}
