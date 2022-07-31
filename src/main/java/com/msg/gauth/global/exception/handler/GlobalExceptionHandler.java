package com.msg.gauth.global.exception.handler;

import com.msg.gauth.global.exception.ErrorResponse;
import com.msg.gauth.global.exception.exceptions.BasicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<ErrorResponse> BasicExceptionHandler(HttpServletRequest request, BasicException ex){
        log.error(request.getRequestURI());
        log.error(ex.getMessage());
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode().getCode()));
    }

}
