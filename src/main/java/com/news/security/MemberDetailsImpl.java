package com.news.security;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MemberDetailsImpl implements UserDetails {

    private String email;
    private String password;
    private String nickname;
    String imageUrl;

    @Builder
    public MemberDetailsImpl(String email, String nickname, String password, String imageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨 있는지 잠겨 있지 않았는지 리턴함 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정이 만료되지 않았는지 리턴함 (true: 만료 안 됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용 가능)인지 리턴함 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }


}
