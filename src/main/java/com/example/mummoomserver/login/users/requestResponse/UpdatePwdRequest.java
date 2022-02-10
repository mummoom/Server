package com.example.mummoomserver.login.users.requestResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UpdatePwdRequest {

    @ApiModelProperty(example = "기존 비밀번호")
    private String lastPassword;

    @ApiModelProperty(example = "변경할 비밀번호")
    @Pattern(regexp = "[a-zA-Z!@#$%^&*-_]{6,20}", message = "6~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다.")
    private String newPassword;

    @Builder
    public UpdatePwdRequest(String lastPassword, String newPassword) {
        this.lastPassword = lastPassword;
        this.newPassword = newPassword;

    }


}
