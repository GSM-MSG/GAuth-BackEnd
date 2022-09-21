package com.msg.gauth.domain.client.exception

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException

class UserNotSameException: BasicException(ErrorCode.USER_NOT_SAME){}