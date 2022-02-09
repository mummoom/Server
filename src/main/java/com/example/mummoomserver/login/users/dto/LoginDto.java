package com.example.mummoomserver.login.users.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "로그인 정보", description = "토큰, 강아지 정보 등록 되어있는지 확인 가능")
public class LoginDto {
    private String token;
    private boolean dog_exist;
}
