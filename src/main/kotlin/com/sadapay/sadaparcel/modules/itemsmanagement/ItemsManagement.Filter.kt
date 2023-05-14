package com.sadapay.sadaparcel.modules.itemsmanagement

import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletResponse


@Component
class ItemFilter : Filter {
    override fun init(filterConfig: FilterConfig?) {}

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        servletRequest: ServletRequest,
        servletResponse: ServletResponse,
        filterChain: FilterChain
    ) {
        val httpServletResponse = servletResponse as HttpServletResponse
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*")
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH")
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600")
        httpServletResponse.setHeader(
            "Access-Control-Allow-Headers",
            "Origin, X-Requested-With, Content-Type, Accept, Authorization"
        )
        httpServletResponse.setHeader("X-SADAPARCEL-APP", "sadaparcel-header")
        filterChain.doFilter(servletRequest, servletResponse)
    }

    override fun destroy() {}
}