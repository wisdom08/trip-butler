package com.news.controller;

import com.news.dto.member.LoginRequestDto;
import com.news.dto.member.LoginResponseDto;
import com.news.dto.member.MemberRequestDto;
import com.news.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member/")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("signup")
    public String signup(@RequestBody MemberRequestDto memberRequestDto) {
        try {
            ResponseEntity.ok(memberService.signup(memberRequestDto));
            return "회원가입 완료";
        }catch (Exception e) {
            return "회원가입 실패";
        }
    }

    // 로그인
    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(memberService.login(loginRequestDto));
    }

    // 로그아웃
    @GetMapping("logout")
    public void logOut(@RequestHeader(value = "Authorization") String token) {
        memberService.logout(token);
    }

    // 리프레시토큰
    @GetMapping("refresh")
    public LoginResponseDto refreshUserInfo(@RequestHeader(value = "Authorization") String token) {
        return memberService.refreshUserInfo(token);
    }
}