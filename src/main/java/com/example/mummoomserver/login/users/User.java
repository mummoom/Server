package com.example.mummoomserver.login.users;


import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.login.authentication.oauth2.account.OAuth2Account;
import com.example.mummoomserver.login.security.AuthorityType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// 회원 테이블과 매핑되는 user entity 클래스

@Entity
@Getter
@Table(name = "User")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column()
    private String password;

    @Column()
    private String imgUrl;

    @Enumerated(value = EnumType.STRING)  // 일반 로그인인지 소셜 로그인인지 확인하는 컬럼
    private UserType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // (점검 필요)
    @JoinColumn(name = "userIdx") // oauth2account 테이블의 social의 id와 연결되어 있음 네이밍 정의 대문자로 하는건지만 확인
    private OAuth2Account social;

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
//   getRole로 대체 됨
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.toString())).collect(Collectors.toList());
//    }

    public void linkSocial(OAuth2Account oAuth2Account) {
        Assert.state(social == null, "하나의 소셜 서비스만 연동할 수 있습니다.");
        this.social = oAuth2Account;
        oAuth2Account.linkUser(this);
    }

    public void unlinkSocial() {
        Assert.state(type.equals(UserType.DEFAULT), "소셜 계정으로 가입된 계정은 연동 해제가 불가능합니다.");
        Assert.state(social != null, "연동된 소셜 계정 정보가 없습니다.");
        this.social.unlinkUser();
        this.social = null;
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
}