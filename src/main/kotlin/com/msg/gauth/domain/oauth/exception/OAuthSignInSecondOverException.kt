package com.msg.gauth.domain.oauth.exception

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException

class OAuthSignInSecondOverException : BasicException(ErrorCode.MANY_REQUEST_OAUTH_SIGN_IN_SECOND) {
}