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
@ApiModel(value = "유저 정보", description = "이메일, 닉네임, 프로필 사진 확인 가능")
public class UserDto {
    private String email;
    private String nickName;
    private String imgUrl;



}


