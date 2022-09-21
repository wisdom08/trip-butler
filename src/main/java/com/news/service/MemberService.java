package com.news.service;

import com.news.dto.TokenDto;
import com.news.dto.member.LoginRequestDto;
import com.news.dto.member.LoginResponseDto;
import com.news.dto.member.MemberRequestDto;
import com.news.dto.member.MemberResponseDto;
import com.news.entity.Member;
import com.news.entity.RefreshToken;
import com.news.jwt.TokenProvider;
import com.news.repository.MemberRepository;
import com.news.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다");
        }
        if (memberRepository.existsByNickname(memberRequestDto.getNickname())) {
            throw new RuntimeException("이미 존재하는 닉네임입니다");
        }

        Member member = memberRequestDto.toMember(passwordEncoder);

        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Member member = memberRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow();

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
                .email(member.getEmail())
                .nickname(member.getNickname())
                .imageUrl(null)
                .build();
    }

    @Transactional
    public void logout(String token) {
        Member member = getMemberByToken(token);
        refreshTokenRepository.deleteByKey(member.getId().toString());
    }

    public Member getMemberByToken(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token.substring(7));
        Long MemberId = Long.parseLong(authentication.getName());
        return memberRepository.findById(MemberId).orElseThrow(() ->
                new IllegalArgumentException("사용자 정보가 없습니다."));
    }

    public LoginResponseDto refreshUserInfo(String token) {

        Member member = getMemberByToken(token);

        return LoginResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .imageUrl(member.getImageUrl())
                .build();
    }




}
