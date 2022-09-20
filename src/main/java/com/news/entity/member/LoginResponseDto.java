package com.news.entity.member;

import com.news.entity.Member;
import com.news.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {

    private String nickname;
    private Role role;
    private String image_url;

    public static LoginResponseDto of(Member member) {
        return new LoginResponseDto(member.getNickname(), member.getRole(), member.getImageUrl());
    }
}
