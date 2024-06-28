package com.msg.gauth.domain.auth.exception

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException

class ExpiredRefreshTokenException : BasicException(ErrorCode.EXPIRED_REFRESH_TOKEN)