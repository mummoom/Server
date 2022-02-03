package com.example.mummoomserver.login.service;


import com.example.mummoomserver.login.users.Role;
import com.example.mummoomserver.login.users.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long userIdx;
    private String nickName;
    private String email;
    private String username;
    private String password;
    private String imgUrl;
    private UserType type;
    private Role role;
    private Collection<? extends GrantedAuthority> authorities;


    @Builder
    public UserDetailsImpl(Long userIdx, String nickName, String email, String username, String password,String imgUrl, UserType type,Role role, Collection<? extends GrantedAuthority> authorities) {
        this.userIdx = userIdx;
        this.nickName = nickName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.imgUrl = imgUrl;
        this.type = type;
        this.role = role;
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