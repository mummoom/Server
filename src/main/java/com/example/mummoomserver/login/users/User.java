package com.example.mummoomserver.login.users;


import com.example.mummoomserver.config.BaseTimeEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;

// 회원 테이블과 매핑되는 user entity 클래스
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long userIdx;

    @Column(nullable = false, length = 20)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username; //이메일 대체할 수도 있는 필드

    @Column() //소셜로그인인 경우에는 사용하지 않는 다는 것에 대한 설정 필요
    private String password;

    @Column()
    private String imgUrl;

    @Enumerated(value = EnumType.STRING)  // 일반 로그인인지 소셜 로그인인지 확인하는 컬럼
    private UserType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Builder
    public User(String username, String nickName, String email, String password,String imgUrl, UserType type, Role role) {
        this.username = username;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.imgUrl = imgUrl;
        this.role = role;
        this.type = type;
    }


    public void updateName(String nickName) {
        this.nickName = nickName;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public User update(String nickName,String email, String imgUrl) {  //update email, update eimgurl, update nickname
        this.nickName = nickName;
        this.email = email;
        //일반 계정이라면 username 도 함께 변경해준다.
        if (type.equals(UserType.DEFAULT))
            this.username = email;
        this.imgUrl = imgUrl;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public String getUsername() {
        return null;
    }

    //시큐리티에서는 UserDetails를 이용해 유저정보를 관리함
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public boolean isAccountNonExpired() {
        return false;
    }


    public boolean isAccountNonLocked() {
        return false;
    }

    public boolean isCredentialsNonExpired() {
        return false;
    }
}