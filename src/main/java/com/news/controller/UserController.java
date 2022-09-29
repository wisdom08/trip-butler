package com.news.controller;

import com.news.dto.ResponseDto;
import com.news.dto.user.LoginRequestDto;
import com.news.dto.user.UserRequestDto;
import com.news.error.ErrorCode;
import com.news.error.exception.BadRequestException;
import com.news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<UserRequestDto> signup(@RequestBody @Valid UserRequestDto userRequestDto) {
        userService.signup(userRequestDto);
        return ResponseDto.success(201, null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(userService.login(loginRequestDto), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @PostMapping("/logout")
    public ResponseDto<?> logout(String email) {
        userService.logout(email);
        return ResponseDto.success(200, null);
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> reload(HttpServletRequest request) {
        return new ResponseEntity<>(userService.refresh(request), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @PostMapping("/emailconfirm")
    public ResponseEntity<?> checkEmailDuplication(@RequestBody String email) throws BadRequestException {

        if (userService.existsByEmail(email)) {
            throw new BadRequestException(ErrorCode.EMAIL_DUPLICATION);
        } else {
            return ResponseEntity.ok("사용 가능한 이메일 입니다.");
        }
    }

    @PostMapping("/nicknameconfirm")
    public ResponseEntity<?> checkNicknameDuplication(@RequestBody String nickname) throws BadRequestException {

        if (userService.existsByNickname(nickname)) {
            throw new BadRequestException(ErrorCode.NICKNAME_DUPLICATION);
        } else {
            return ResponseEntity.ok("사용 가능한 닉네임 입니다.");
        }
    }

}