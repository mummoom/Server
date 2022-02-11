package com.example.mummoomserver.login.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor

public class GoogleUser {
    private String id;
    private String email;
    private boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String link;
    private String picture;
    private String locale;



    public UserDto toUserDto(String email, String name,String imgUrl){
        return new UserDto(email,name,imgUrl);
    }

}