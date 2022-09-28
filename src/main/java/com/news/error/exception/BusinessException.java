package com.news.error.exception;


import com.news.error.ErrorCode;

/* 공통 비즈니스 에러를 위한 익셉션 */
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;


    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
