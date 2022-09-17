package com.msg.gauth.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.ErrorResponse
import com.msg.gauth.global.exception.exceptions.BasicException
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionFilter(
    private val objectMapper: ObjectMapper
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: BasicException) {
            sendError(response, e.errorCode)
        } catch (e: Exception) {
            sendError(response, ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    private fun sendError(res: HttpServletResponse, errorCode: ErrorCode) {
        val errorResponse = ErrorResponse(errorCode)
        val responseString = objectMapper!!.writeValueAsString(errorResponse)
        res.status = errorCode.code
        res.contentType = MediaType.APPLICATION_JSON_VALUE
        res.writer.write(responseString)
    }
}