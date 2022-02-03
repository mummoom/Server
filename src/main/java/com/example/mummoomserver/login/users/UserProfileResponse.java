package com.example.mummoomserver.login.users;

import com.example.mummoomserver.login.util.DateConvertor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserProfileResponse {

    private Long userIdx;
    private String nickName;
    private String email;
    private List<Role> role;
    private String socialProvider;
    private Long linkedAt;

    @Builder
    public UserProfileResponse(Long userIdx, String nickName, String email, List<Role> role, String socialProvider, LocalDateTime linkedAt) {
        this.userIdx = userIdx;
        this.nickName = nickName;
        this.email = email;
        this.role = role;
        this.socialProvider = socialProvider;
        if (linkedAt != null)
            this.linkedAt = DateConvertor.toEpochMilli(linkedAt);
    }
}
