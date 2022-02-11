package com.example.mummoomserver.login.users.requestResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UpdateNickNameRequest {

    @ApiModelProperty(example = "변경할 닉네임")
    @Size(min = 1, max = 20, message = "이름이 입력되지 않았거나 너무 긴 이름입니다.")
    private String nickName;

    @Builder
    public UpdateNickNameRequest(String nickName) {
        this.nickName = nickName;

    }

}
