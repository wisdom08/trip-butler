package com.news.service;

import com.news.dto.TokenDto;
import com.news.dto.user.LoginRequestDto;
import com.news.dto.user.LoginResponseDto;
import com.news.dto.user.UserRequestDto;
import com.news.dto.user.UserResponseDto;
import com.news.entity.RefreshToken;
import com.news.entity.User;
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

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public UserResponseDto signup(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다");
        }
        if (userRepository.existsByNickname(userRequestDto.getNickname())) {
            throw new RuntimeException("이미 존재하는 닉네임입니다");
        }

        User user = userRequestDto.toUser(passwordEncoder);

        return UserResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow();

        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        refreshTokenRepository.save(RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build());

        return LoginResponseDto.builder()
                .grantType(tokenDto.getGrantType())
                .accessToken(tokenDto.getAccessToken())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .imageUrl(null)
                .build();
    }

    @Transactional
    public void logout(String token) {
        User user = getUserByToken(token);
        refreshTokenRepository.deleteByKey(user.getId().toString());
    }

    public User getUserByToken(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token.substring(7));
        Long UserId = Long.parseLong(authentication.getName());
        return userRepository.findById(UserId).orElseThrow(() ->
                new IllegalArgumentException("사용자 정보가 없습니다."));
    }

    public LoginResponseDto refreshUserInfo(String token) {

        User user = getUserByToken(token);

        return LoginResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .build();
    }




}
