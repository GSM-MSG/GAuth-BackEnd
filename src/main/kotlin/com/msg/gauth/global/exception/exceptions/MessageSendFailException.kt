package com.msg.gauth.global.exception.exceptions

import com.msg.gauth.global.exception.ErrorCode

class MessageSendFailException: BasicException(ErrorCode.MAIL_SEND_FAIL)