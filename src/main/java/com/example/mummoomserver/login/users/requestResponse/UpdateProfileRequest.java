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
    @ApiModelProperty(example = "변경할 닉네임")
    @Size(min = 1, max = 20, message = "이름이 입력되지 않았거나 너무 긴 이름입니다.")
    private String nickName;

    @ApiModelProperty(example = "변경할 이미지")
    private String imgUrl;

    @Builder
    public UpdateProfileRequest(String imgUrl, String nickName) {
        this.imgUrl = imgUrl;
        this.nickName = nickName;

    }
}
