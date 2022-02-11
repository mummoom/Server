package com.example.mummoomserver.login.users.requestResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UpdateImgRequest {

    @ApiModelProperty(example = "변경할 이미지")
    private String imgUrl;

    @Builder
    public UpdateImgRequest(String imgUrl) {
        this.imgUrl = imgUrl;

    }

}
