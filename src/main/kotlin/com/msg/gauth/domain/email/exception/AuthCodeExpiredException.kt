package com.msg.gauth.domain.email.exception

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException

class AuthCodeExpiredException : BasicException(
    ErrorCode.AUTH_CODE_EXPIRED
)