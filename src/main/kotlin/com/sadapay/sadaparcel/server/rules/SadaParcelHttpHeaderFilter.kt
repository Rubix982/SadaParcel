/*
 * Copyright 2023, Saif Ul Islam @ SadaParcel, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.sadapay.sadaparcel.server.rules

import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletResponse


@Component
class SadaParcelHttpHeaderFilter : Filter {
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