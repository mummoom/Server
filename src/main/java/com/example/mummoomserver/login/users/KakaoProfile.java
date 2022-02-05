package com.example.mummoomserver.login.users;

import lombok.Data;

@Data
public class KakaoProfile{

    public Integer id;
    public String connected_At;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Data
    public class Properties{
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }
    @Data
    public class Profile {
        public String nickname;
        public String thumbnail_image_irl;
        public String profile_image_url;
    }
    @Data
    public class KakaoAccount {
        public Boolean profile_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
    }
}





