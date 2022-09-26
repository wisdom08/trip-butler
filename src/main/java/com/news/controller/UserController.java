package com.news.controller;

import com.news.dto.user.LoginRequestDto;
import com.news.dto.user.LoginResponseDto;
import com.news.dto.user.UserRequestDto;
import com.news.error.exception.BadRequestException;
import com.news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.signup(userRequestDto), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    // 로그인
    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        userService.logout(request);
        return new ResponseEntity<>("로그아웃이 완료되었습니다.", HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    // 리프레시토큰
    @GetMapping("/refresh")
    public ResponseEntity<?> reload(HttpServletRequest request) {
        return new ResponseEntity<>(userService.refresh(request), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    // 이메일 중복검사
    @PostMapping("/emailcheck")
    public ResponseEntity<?> checkEmailDuplication(@RequestBody String email) throws BadRequestException {

        if (userService.existsByEmail(email)) {
            throw new BadRequestException("이미 사용중인 이메일 입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 이메일 입니다.");
        }
    }

    // 닉네임 중복검사
    @PostMapping("/nicknamecheck")
    public ResponseEntity<?> checkNicknameDuplication(@RequestBody String nickname) throws BadRequestException {

        if (userService.existsByNickname(nickname)) {
            throw new BadRequestException("이미 사용중인 닉네임 입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 닉네임 입니다.");
        }
    }

}