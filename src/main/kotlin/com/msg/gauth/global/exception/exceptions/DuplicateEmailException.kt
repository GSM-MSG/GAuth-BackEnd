package com.msg.gauth.global.exception.exceptions

import com.msg.gauth.global.exception.ErrorCode

class DuplicateEmailException: BasicException(ErrorCode.DUPLICATE_EMAIL)