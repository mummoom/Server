package com.example.mummoomserver.login.users;


import com.example.mummoomserver.config.BaseTimeEntity;
import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.Post.Post;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// 회원 테이블과 매핑되는 user entity 클래스
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false) //소셜로그인인 경우에는 사용하지 않는 다는 것에 대한 설정 필요
    private String password;

    @Column(nullable = false, length = 20)
    private String nickName;

    @Column
    private String imgUrl;

    @Enumerated(value = EnumType.STRING)  // 일반 로그인인지 소셜 로그인인지 확인하는 컬럼
    private UserType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public User(String nickName, String email, String password, String imgUrl, UserType type, Role role) {
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

    public User update(String nickName, String email, String imgUrl) {  //update email, update eimgurl, update nickname
        this.nickName = nickName;
        //일반 계정이라면 이메일도 함께 변경해준다.
        if (type.equals(UserType.DEFAULT))
            this.email = email;
        this.imgUrl = imgUrl;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}