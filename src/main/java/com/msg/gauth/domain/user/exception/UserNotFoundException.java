package com.msg.gauth.domain.user.exception;

import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.exceptions.BasicException;

public class UserNotFoundException extends BasicException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}