package com.msg.gauth.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private Boolean success;
    private String msg;
    private Integer code;

    public ErrorResponse(ErrorCode errorCode){
        this.msg=errorCode.getMsg();
        this.success=errorCode.getSuccess();
        this.code=errorCode.getCode();
    }
}
