package com.example.mummoomserver.login.security;

import com.example.mummoomserver.login.users.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// 인증된 유저의 데이터를 담는 클래스

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;
    private UserType type;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public UserDetailsImpl(Long id, String name, String email, String username, String password, UserType type, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.type = type;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
