package com.news.service;

import com.news.dto.TokenDto;
import com.news.dto.user.LoginRequestDto;
import com.news.dto.user.LoginResponseDto;
import com.news.dto.user.UserRequestDto;
import com.news.entity.User;
import com.news.error.ErrorCode;
import com.news.error.exception.InvalidValueException;
import com.news.jwt.TokenProvider;
import com.news.repository.RefreshTokenRepository;
import com.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public void signup(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new InvalidValueException(ErrorCode.EMAIL_DUPLICATION);
        }
        if (userRepository.existsByNickname(userRequestDto.getNickname())) {
            throw new InvalidValueException(ErrorCode.NICKNAME_DUPLICATION);
        }

        User user = userRequestDto.toUser(passwordEncoder);
        userRepository.save(user);
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        User user = isPresentUser(loginRequestDto.getEmail());

        // 유저 없을시 익셉션 처리
        if (null == user) {
           throw new InvalidValueException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        // 비밀번호 체크, 실패시 익셉션 처리
        if (!user.validatePassword(passwordEncoder, loginRequestDto.getPassword())) {
            throw new InvalidValueException(ErrorCode.NOTEQUAL_INPUT_PASSWORD);
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(user);

        return LoginResponseDto.builder()
                        .grantType(tokenDto.getGrantType())
                        .accessToken(tokenDto.getAccessToken())
                        .email(user.getEmail())
                        .nickname(user.getNickname())
                        .imageUrl(null)
                        .build();
    }

    @Transactional
    public void logout(String email) {
        Long userId = userRepository.findIdByEmail(email);
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Transactional
    public TokenDto refresh(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            throw new InvalidValueException(ErrorCode.INVALID_TOKEN);
        }
        User userFromRefreshTokenRepo = tokenProvider.getUserFromRefreshTokenRepo(request.getHeader("Refresh-Token"));

        return tokenProvider.generateTokenDto(userFromRefreshTokenRepo);
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
