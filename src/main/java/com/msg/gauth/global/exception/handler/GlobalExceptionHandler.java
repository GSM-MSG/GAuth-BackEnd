package com.msg.gauth.global.exception.handler;

import com.msg.gauth.global.annotation.logger.log4k;
import com.msg.gauth.global.exception.ErrorResponse;
import com.msg.gauth.global.exception.exceptions.BasicException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @log4k
    Logger log;

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<ErrorResponse> BasicExceptionHandler(HttpServletRequest request, BasicException ex){
        log.error(request.getRequestURI());
        log.error(ex.getMessage());
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode().getCode()));
    }

}
