package com.news.dto.user;


import com.news.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {

    private Long id;

    private String email;

    private String nickname;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getNickname()

        );
    }
}
