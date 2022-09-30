package com.news.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {


    // Common
    INVALID_INPUT_VALUE(400, " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405,  " Invalid Input Value"),
    ENTITY_NOT_FOUND(400,  " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    INVALID_TYPE_VALUE(400, " Invalid Type Value"),

    // 유저
    HANDLE_ACCESS_DENIED(401, "로그인이 필요합니다."),
    INVALID_INPUT_USERNAME(400, "아이디를 6자 이상 입력해 주세요"),
    NOTEQUAL_INPUT_PASSWORD(400,  "비밀번호가 일치하지 않습니다"),
    INVALID_TOKEN(400, "잘못된 토큰입니다."),
    EMAIL_DUPLICATION(400, "이미 등록된 이메일입니다."),
    NICKNAME_DUPLICATION(400, "이미 등록된 닉네임입니다."),
    LOGIN_INPUT_INVALID(400, "로그인 정보를 다시 확인해 주세요."),
    LOGIN_STATE_INVALID(400, "로그인 상태를 다시 확인해 주세요.");


    private final int status; // 상태 코드를 상수로 선언해둔 HttpStatus 타입의 멤버, 예외에 대한 상태 코드(status)와 이름(error)을 처리하는 데 사용된다.
    private final String message; // 예외에 대한 응답 메시지(message)를 처리하는 데 사용되는 멤버

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
    public int getStatus() {
        return status;
    }
}
