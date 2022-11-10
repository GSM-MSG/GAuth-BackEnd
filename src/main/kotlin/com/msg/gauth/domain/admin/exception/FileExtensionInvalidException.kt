package com.msg.gauth.domain.admin.exception

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException

class FileExtensionInvalidException: BasicException(
    ErrorCode.FILE_EXTENSION_INVALID
)