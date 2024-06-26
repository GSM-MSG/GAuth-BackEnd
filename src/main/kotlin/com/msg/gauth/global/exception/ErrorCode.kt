package com.msg.gauth.global.exception

enum class ErrorCode(
    val msg: String,
    val code: Int
) {
    BAD_REQUEST("잘못된 요청", 400),
    PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다.", 400),
    CLIENT_SECRET_MISMATCH("클라이언트 시크릿이 일치하지 않습니다.", 400),
    FILE_EXTENSION_INVALID("파일 확장자가 유효하지 않습니다.", 400),
    ALREADY_AUTHENTICATED_EMAIL("이미 인증된 메일 입니다.", 400),
    INVALID_DELEGATE_USER("유효하지 않은 위임자입니다.", 400),
    SAME_PASSWORD_AND_NEW_PASSWORD("기본 비밀번호와 새 비밀번호가 일치합니다.", 400),
    ALREADY_EXIST_COWORKER("해당 서비스에 이미 존재하는 공동작업자입니다.", 400),
    ALREADY_EXIST_USER("이미 존재하는 유저입니다.", 400),

    AUTH_CODE_EXPIRED("메일 인증이 만료되었습니다.", 401),
    UNAUTHORIZED("권한 없음", 401),
    EXPIRED_TOKEN("토큰 만료", 401),
    INVALID_TOKEN("토큰 변질", 401),
    EXPIRED_REFRESH_TOKEN("리프레시 토큰이 만료되었습니다", 401),
    INVALID_REFRESH_TOKEN("리프레시 토큰이 변질되었습니다", 401),
    EMAIL_NOT_VERIFIED("인증된 이메일이 아닙니다.", 401),
    AUTH_CODE_NOT_VERIFIED("인증 코드가 확인되지 않았습니다", 401),
    USER_NOT_MATCH("유저가 일치하지 않습니다", 401),
    OAUTH_CODE_EXPIRED("oauth 코드가 만료되었습니다.", 401),

    BAD_USER_ROLE_REQUEST("잘못된 유저 역할입니다..", 400),

    FORBIDDEN("금지된 요청입니다.", 403),
    USER_STATE_PENDING("유저가 보류중 입니다.", 403),
    SIGN_IN_BAN("로그인이 금지된 계정입니다.", 403),
    TEMP_BAN("계정이 1분 금지된 상태입니다.", 403),
    OAUTH_SIGN_IN_BAN("oauth 로그인이 금지된 계정입니다.", 403),

    NOT_FOUND("리소스를 찾을수 없음", 404),
    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다.", 404),
    CLIENT_NOT_FOUND("해당 클라이언트를 찾을 수 없습니다.", 404),
    COWORKER_NOT_FOUND("해당하는 공동작업자를 찾을 수 없습니다.", 404),

    DUPLICATE_EMAIL("중복되는 이메일입니다.", 409),

    MANY_REQUEST_EMAIL_AUTH("15분에 최대 3번 이메일 인증을 요청할 수 있습니다.", 429),
    MANY_REQUEST_SIGN_IN_SECOND("5초에 20번 이상 로그인 요청이 들어왔습니다.", 429),
    MANY_REQUEST_SIGN_IN_MINUTE("1분에 10회 이상 로그인 요청이 들어왔습니다.", 429),
    MANY_REQUEST_OAUTH_SIGN_IN_SECOND("5초에 20번 이상 oauth 로그인 요청이 들어왔습니다.", 429),
    MANY_REQUEST_OAUTH_SIGN_IN_MINUTE("1분에 10회 이상 oauth 로그인 요청이 들어왔습니다.", 429),

    MAIL_SEND_FAIL("메일을 보내는데 실패했습니다.", 500),
    INTERNAL_SERVER_ERROR("서버 내부 에러", 500);
}