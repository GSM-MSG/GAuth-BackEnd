package com.msg.gauth.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR("서버 내부 에러", 500),

    BAD_REQUEST("잘못된 요청", 400),
    PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다.", 400),

    UNAUTHORIZED("권한 없음", 401),
    EXPIRED_TOKEN("토큰 만료", 401),
    INVALID_TOKEN("토큰 변질", 401),
    EXPIRED_REFRESH_TOKEN("리프레시 토큰이 만료되었습니다", 401),
    INVALID_REFRESH_TOKEN("리프레시 토큰이 변질되었습니다", 401),

    NOT_FOUND("리소스를 찾을수 없음", 404),
    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다.", 404),
    
    DUPLICATE_EMAIL("중복되는 이메일입니다.", 409),

    MAIL_SEND_FAIL("메일을 보내는데 실패했습니다.", 500),
    ;

    private String msg;
    private Integer code;
}
