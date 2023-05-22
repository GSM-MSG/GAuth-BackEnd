package com.msg.gauth.global.filter

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
        listOf(
            "client ip = ${request.remoteAddr}",
            "request method = ${request.method}",
            "request url = ${request.requestURI}",
            "client info = ${request.getHeader("User-Agent")}"
        ).forEach { log.info(it) }
        filterChain.doFilter(request, response)
        log.info("response status = ${response.status}")
    }
}