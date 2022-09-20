package com.news.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    private String email;
    private String nickname;
    private String password;
    private String passwordCheck;
}
