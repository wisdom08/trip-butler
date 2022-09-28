package com.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private boolean success;
    private T data;
    private int code;


    public static <T> ResponseDto<T> success(int code, T data) {
        return new ResponseDto<>(code, true, data);
    }


    @Builder
    public ResponseDto(int code, boolean success, T data) {
        this.code = code;
        this.success = success;
        this.data = data;
    }
}
