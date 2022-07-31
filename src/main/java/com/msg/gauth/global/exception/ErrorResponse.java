package com.msg.gauth.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String msg;
    private Integer code;

    public ErrorResponse(ErrorCode errorCode){
        this.msg=errorCode.getMsg();
        this.code=errorCode.getCode();
    }
}
