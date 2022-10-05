package com.news.controller;

import com.news.dto.ResponseDto;
import com.news.dto.user.LoginRequestDto;
import com.news.dto.user.UserRequestDto;
import com.news.error.exception.BadRequestException;
import com.news.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/email")
    public boolean checkEmailDuplication(@RequestBody String email) {
        return userService.existsByEmail(email);
    }

    @PostMapping("/nickname")
    public boolean checkNicknameDuplication(@RequestBody String nickname) throws BadRequestException {
        return userService.existsByNickname(nickname);
    }

}