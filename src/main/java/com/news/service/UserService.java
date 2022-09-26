package com.news.service;

import com.news.dto.ResponseDto;
import com.news.dto.TokenDto;
import com.news.dto.user.LoginRequestDto;
import com.news.dto.user.LoginResponseDto;
import com.news.dto.user.UserRequestDto;
import com.news.dto.user.UserResponseDto;
import com.news.entity.RefreshToken;
import com.news.entity.User;
import com.news.error.ErrorCode;
import com.news.jwt.TokenProvider;
import com.news.repository.RefreshTokenRepository;
import com.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public ResponseDto<?> signup(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            return ResponseDto.fail(ErrorCode.EMAIL_DUPLICATION);
        }
        if (userRepository.existsByNickname(userRequestDto.getNickname())) {
            return ResponseDto.fail(ErrorCode.NICKNAME_DUPLICATION);
        }

        User user = userRequestDto.toUser(passwordEncoder);
        userRepository.save(user);
        return ResponseDto.success("회원가입에 성공하였습니다.");
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        //유저 이메일 존재 체크
        User user = isPresentUser(loginRequestDto.getEmail());

        //유저 없을시 익셉션 처리
        if (null == user) {
            throw new IllegalArgumentException("로그인 정보를 다시 확인해 주세요.");
        }

        //비밀번호 체크, 실패시 익셉션 처리
        if (!user.validatePassword(passwordEncoder, loginRequestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 다시 확인해 주세요.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(user);

        refreshTokenRepository.save(RefreshToken.builder()
                .id(user.getId())
                .user(user)
                .refreshTokenValue(tokenDto.getRefreshToken())
                .build());

        return LoginResponseDto.builder()
                .grantType(tokenDto.getGrantType())
                .accessToken(tokenDto.getAccessToken())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .imageUrl(null)
                .build();
    }

    public ResponseDto<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }
        User user = tokenProvider.getUserFromAuthentication();
        if (null == user) {
            return ResponseDto.fail(ErrorCode.LOGIN_STATE_INVALID);
        }

        return tokenProvider.deleteRefreshToken(user);
    }

    @Transactional
    public ResponseDto<?> refresh(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }
        User userFromRefreshTokenRepo = tokenProvider.getUserFromRefreshTokenRepo(request.getHeader("Refresh-Token"));
        // 토큰 재발행
        TokenDto tokenDto = tokenProvider.generateTokenDto(userFromRefreshTokenRepo);

        return ResponseDto.success(tokenDto);
    }

    @Transactional(readOnly = true)
    public User isPresentUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

    @Transactional
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    @Transactional
    public boolean existsByNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }
}
