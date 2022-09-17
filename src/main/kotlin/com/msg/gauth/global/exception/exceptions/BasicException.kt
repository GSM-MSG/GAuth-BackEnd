package com.msg.gauth.global.exception.exceptions

import com.msg.gauth.global.exception.ErrorCode

open class BasicException(val errorCode: ErrorCode): RuntimeException(errorCode.msg)