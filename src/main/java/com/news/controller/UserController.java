package com.news.controller;

import com.news.dto.user.LoginRequestDto;
import com.news.dto.user.LoginResponseDto;
import com.news.dto.user.UserRequestDto;
import com.news.dto.user.UserResponseDto;
import com.news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid UserRequestDto userRequestDto) {
        try {
            return ResponseEntity.ok(userService.signup(userRequestDto));
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 로그인
    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    // 로그아웃
    @GetMapping("logout")
    public void logOut(@RequestHeader(value = "Authorization") String token) {
        userService.logout(token);
    }

    // 리프레시토큰
    @GetMapping("refresh")
    public LoginResponseDto refreshUserInfo(@RequestHeader(value = "Authorization") String token) {
        return userService.refreshUserInfo(token);
    }
}