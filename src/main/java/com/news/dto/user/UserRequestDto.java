package com.news.dto.user;

import com.news.entity.Role;
import com.news.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {
    @NotBlank(message = "이메일을 입력해주세요")
    @Size(min = 4, max = 32)
    @Pattern(regexp = "^([\\w\\.\\_\\-])*[a-zA-Z0-9]+([\\w\\.\\_\\-])*([a-zA-Z0-9])+([\\w\\.\\_\\-])+@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,8}$")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 4, max = 12)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?!.*[^a-zA-z0-9]).{4,12}$")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(min = 4, max = 12)
    @Pattern(regexp = "[a-zA-Z\\d]*${3,12}")
    private String nickname;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build();
    }
}