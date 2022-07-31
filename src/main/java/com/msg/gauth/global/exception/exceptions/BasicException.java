package com.msg.gauth.global.exception.exceptions;

import com.msg.gauth.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BasicException extends RuntimeException{

    private ErrorCode errorCode;

    public BasicException(ErrorCode errorCode){
        super(errorCode.getMsg());
        this.errorCode=errorCode;
    }
}
