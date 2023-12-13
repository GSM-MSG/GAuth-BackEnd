package com.msg.gauth.global.exception.handler

import com.msg.gauth.global.annotation.logger.log4k
import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.ErrorResponse
import com.msg.gauth.global.exception.exceptions.BasicException
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionHandler {
    private lateinit var log: Logger

    @log4k
    @ExceptionHandler(BasicException::class)
    fun basicExceptionHandler(request: HttpServletRequest, ex: BasicException): ResponseEntity<ErrorResponse> {
        log.error(request.requestURI)
        log.error(ex.message)
        val errorResponse = ErrorResponse(ex.errorCode)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(ex.errorCode.code))
    }

    @ExceptionHandler(BindException::class)
    fun bindExceptionHandler(e: BindException): ResponseEntity<*> {
        val errorMap: MutableMap<String, String?> = HashMap()
        for (error in e.fieldErrors) {
            errorMap[error.field] = error.defaultMessage
        }
        return ResponseEntity<Map<String, String?>>(errorMap, HttpStatus.BAD_REQUEST)
    }

    @log4k
    @ExceptionHandler(NoHandlerFoundException::class)
    fun arithmeticExceptionHandler(request: HttpServletRequest, e: NoHandlerFoundException): ResponseEntity<ErrorResponse>{
        log.error(request.requestURI)
        val errorResponse = ErrorResponse(ErrorCode.FORBIDDEN)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(ErrorCode.FORBIDDEN.code))
    }
}