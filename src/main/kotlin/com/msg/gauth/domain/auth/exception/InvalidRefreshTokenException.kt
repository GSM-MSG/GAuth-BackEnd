package com.msg.gauth.domain.auth.exception

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException

class InvalidRefreshTokenException : BasicException(ErrorCode.INVALID_REFRESH_TOKEN)