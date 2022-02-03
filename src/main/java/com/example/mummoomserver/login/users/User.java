package com.example.mummoomserver.login.users;


import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.Post.Post;
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
@Table(name = "User") //원래 : tbl_user
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Column(nullable = false, length = 20)
    private String nickName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username; //이메일 대체할 수도 있는 필드

    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserType type;

    @OneToMany(mappedBy = "userIdx")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "userIdx")
    private List<Comment> comments = new ArrayList<>();

    @ElementCollection(targetClass = AuthorityType.class) // 테이블 형태로 저장되는 colleciton 객채(여러 원소를 담는 자료 구조)
    @CollectionTable(name = "UserAuthority", joinColumns = @JoinColumn(name = "userIdx"))
    @Enumerated(EnumType.STRING)
    private List<AuthorityType> authorities = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userIdx") // oauth2account 테이블의 social의 id와 연결되어 있음 네이밍 정의 대문자로 하는건지만 확인
    private OAuth2Account social;

    @Builder
    public User(String username, String nickName, String email, String password, UserType type) {
        this.username = username;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.authorities.add(AuthorityType.ROLE_MEMBER);
        this.type = type;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.toString())).collect(Collectors.toList());
    }

    public void updateName(String nickName) {
        this.nickName = nickName;
    }

    public void updateEmail(String email) {
        this.email = email;
        //일반 계정이라면 username 도 함께 변경해준다.
        if (type.equals(UserType.DEFAULT))
            this.username = email;
    }

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
}