package com.news.security;

import com.news.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MemberDetailsImpl implements MemberDetails {
    private final Member member;

    public MemberDetailsImpl(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }

    public String getPassword() {
        return this.member.getPassword();
    }

    public String getNickname() {
        return this.member.getNickname();
    }

    public String getEmail() {
        return this.member.getEmail();
    }

    public String getImageUrl() {
        return this.member.getImageUrl();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}

