package com.msg.gauth.global.exception

class ErrorResponse(errorCode: ErrorCode) {
    val msg: String
    val code: Int

    init {
        msg = errorCode.msg
        code = errorCode.code
    }
}