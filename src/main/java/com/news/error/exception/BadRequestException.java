package com.news.error.exception;

import com.news.error.ErrorCode;

public class BadRequestException extends BusinessException{

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}