package com.msg.gauth.domain.oauth.exception

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException

class UserStatePendingException : BasicException(
    ErrorCode.USER_STATE_PENDING
)