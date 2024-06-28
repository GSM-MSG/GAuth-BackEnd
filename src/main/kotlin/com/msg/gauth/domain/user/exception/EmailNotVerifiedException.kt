package com.msg.gauth.domain.user.exception

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException

class EmailNotVerifiedException : BasicException(
    ErrorCode.EMAIL_NOT_VERIFIED
)