package com.example.mummoomserver.login.users.requestResponse;

import com.example.mummoomserver.login.users.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {

    private Long userIdx;
    private String nickName;
    private String email;
    private Role role;

    @Builder
    public UserProfileResponse(Long userIdx, String nickName, String email, Role role) {
        this.userIdx = userIdx;
        this.nickName = nickName;
        this.email = email;
        this.role = role;

    }
}
