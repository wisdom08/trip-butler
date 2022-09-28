package com.news.dto.user;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
    private String grantType;
    private String accessToken;

    private String email;
    private String nickname;
    private String imageUrl;

    @Builder
    public LoginResponseDto(String grantType, String accessToken, String email, String nickname, String imageUrl) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }
}
