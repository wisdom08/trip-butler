package com.news.dto.user;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
    private String grantType;
    private String accessToken;

    private Long accessTokenExpiresIn;
    private String email;
    private String nickname;

    @Builder
    public LoginResponseDto(String grantType, String accessToken, Long accessTokenExpiresIn, String email, String nickname) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.email = email;
        this.nickname = nickname;
    }
}
