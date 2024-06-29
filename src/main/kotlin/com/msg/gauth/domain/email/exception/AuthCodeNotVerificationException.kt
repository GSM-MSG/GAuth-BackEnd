package com.msg.gauth.domain.email.exception

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException

class AuthCodeNotVerificationException : BasicException(
    ErrorCode.AUTH_CODE_NOT_VERIFIED
)