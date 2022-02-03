package com.example.mummoomserver.login.authentication.oauth2.userInfo;

import com.example.mummoomserver.login.users.Role;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserType;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private Long userIdx;
    private String nickName;
    private String email;
    private String username;
    private String password;
    private String imgUrl;
    private UserType type;
    private Role role;

    public SessionUser(User user) {
        this.userIdx = user.getUserIdx();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.imgUrl = user.getImgUrl();
        this.type = user.getType();
//        this.role = user.getRole();
    }
}
