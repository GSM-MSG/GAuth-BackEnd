package com.msg.gauth.domain.auth.exception;

import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.exceptions.BasicException;

public class PasswordMismatchException extends BasicException {
    public PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
