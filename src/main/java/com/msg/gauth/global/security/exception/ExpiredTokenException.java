package com.msg.gauth.global.security.exception;

import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.exceptions.BasicException;

public class ExpiredTokenException extends BasicException {

    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
