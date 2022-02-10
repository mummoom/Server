package com.example.mummoomserver.login.users.requestResponse;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class WithdrawRequest {

    @ApiModelProperty(example = "기존 비밀번호")
    private String withdrawPassword;

    @ApiModelProperty(example = "탈퇴사유")
    private String withdrawReason;

    @Builder
    public WithdrawRequest(String withdrawPassword, String withdrawReason) {
        this.withdrawPassword = withdrawPassword;
        this.withdrawReason = withdrawReason;

    }


}
