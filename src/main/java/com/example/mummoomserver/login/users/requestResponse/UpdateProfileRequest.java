package com.example.mummoomserver.login.users.requestResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UpdateProfileRequest {

    @Size(min = 1, max = 20, message = "이름이 입력되지 않았거나 너무 긴 이름입니다.")
    private String nickName;

    @ApiModelProperty(example = "변경할 비밀번호")
    @Pattern(regexp = "[a-zA-Z!@#$%^&*-_]{6,20}", message = "6~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다.")
    private String password;

    @ApiModelProperty(example = "등록할 프로필 이미지")
    private String imgUrl;

    @Builder
    public UpdateProfileRequest(String nickName, String imgUrl, String password) {
        this.nickName = nickName;
        this.password = password;
        this.imgUrl = imgUrl;
    }
}
