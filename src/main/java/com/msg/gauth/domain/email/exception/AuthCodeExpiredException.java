package com.msg.gauth.domain.email.exception;

import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.exceptions.BasicException;

public class AuthCodeExpiredException extends BasicException {
    public AuthCodeExpiredException() {
        super(ErrorCode.AUTH_CODE_EXPIRED);
    }
}
