package com.msg.gauth.domain.client.exception

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException

class CoworkerNotFoundException : BasicException(
    ErrorCode.COWORKER_NOT_FOUND
)