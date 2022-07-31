package com.msg.gauth.global.security.exception;

import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.exceptions.BasicException;

public class InvalidTokenException extends BasicException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
