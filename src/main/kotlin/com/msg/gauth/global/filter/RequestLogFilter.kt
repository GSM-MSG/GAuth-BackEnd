package com.msg.gauth.global.filter

import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.BasicException
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequestLogFilter : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this::class.simpleName)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        log.info("=========================")

        listOf(
            "client ip = ${request.remoteAddr}",
            "request method = ${request.method}",
            "request url = ${request.requestURI}",
            "client info = ${request.getHeader("User-Agent")}"
        ).forEach {
            log.info(it)
        }

        log.info("=========================")

        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            log.error("=========================")
            log.error(e.cause.toString())
        }
        
        log.info("=========================")
        log.info("response status = ${response.status}")
        log.info("=========================")
    }
}