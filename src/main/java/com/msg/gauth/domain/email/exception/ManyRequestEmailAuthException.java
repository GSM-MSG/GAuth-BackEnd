package com.msg.gauth.domain.email.exception;

import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.exceptions.BasicException;

public class ManyRequestEmailAuthException extends BasicException {
    public ManyRequestEmailAuthException() {
        super(ErrorCode.MANY_REQUEST_EMAIL_AUTH);
    }
}
