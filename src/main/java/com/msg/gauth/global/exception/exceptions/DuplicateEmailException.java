package com.msg.gauth.global.exception.exceptions;

import com.msg.gauth.global.exception.ErrorCode;

public class DuplicateEmailException extends BasicException{
    public DuplicateEmailException(ErrorCode errorCode){
        super(errorCode);
    }
}
