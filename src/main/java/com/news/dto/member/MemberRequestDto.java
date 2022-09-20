package com.news.dto.member;

import com.news.entity.Member;
import com.news.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
//    @NotBlank(message = "아이디를 입력해주세요")
//    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?!.*[^a-zA-z0-9]).{6,10}$",
//            message = "닉네임은 6~10 자리이면서 1개 이상의 알파벳, 숫자를 포함해야합니다.")
    private String email;

//    @NotBlank(message = "비밀번호를 입력해주세요")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?!.*[^a-zA-z0-9]).{4,12}$",
//            message = "비밀번호는 4~12 자리이면서 1개 이상의 알파벳, 숫자를 포함해야합니다.")
    private String password;
    private String nickname;
    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build();
    }
}