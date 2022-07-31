package com.msg.gauth.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.ErrorResponse;
import com.msg.gauth.global.exception.exceptions.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class ExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BasicException e) {
            sendError(response, e.getErrorCode());
        } catch (Exception e) {
            sendError(response, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendError(HttpServletResponse res, ErrorCode errorCode) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        String responseString = objectMapper.writeValueAsString(errorResponse);

        res.setStatus(errorCode.getCode());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter().write(responseString);
    }
}
