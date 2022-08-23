package com.msg.gauth.global.exception.exceptions;

import com.msg.gauth.global.exception.ErrorCode;

public class MessageSendFailException extends BasicException{
    public MessageSendFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
