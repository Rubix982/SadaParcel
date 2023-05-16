package com.sadapay.sadaparcel.server.logging

import org.apache.commons.io.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "RequestCachingFilter", urlPatterns = ["/*"])
class RequestCachingFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cachedHttpServletRequest = CachedHttpServletRequest(request)
        LOGGER.info("REQUEST DATA: " + IOUtils.toString(cachedHttpServletRequest.inputStream, StandardCharsets.UTF_8))
        filterChain.doFilter(cachedHttpServletRequest, response)
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(RequestCachingFilter::class.java)
    }
}