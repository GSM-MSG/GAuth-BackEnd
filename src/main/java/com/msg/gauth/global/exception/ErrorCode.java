package com.msg.gauth.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR("서버 내부 에러", 500, false),
    BAD_REQUEST("잘못된 요청", 400, false),
    UNAUTHORIZED("권한 없음", 401, false),
    NOT_FOUND("리소스를 찾을수 없음", 404, false),
    ;

    private String msg;
    private Integer code;
    private Boolean success;
}
