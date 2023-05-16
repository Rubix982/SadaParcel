package com.sadapay.sadaparcel.server.logging

import org.springframework.util.StreamUtils
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper


class CachedHttpServletRequest(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private val cachedPayload: ByteArray

    init {
        val requestInputStream: InputStream = request.inputStream
        cachedPayload = StreamUtils.copyToByteArray(requestInputStream)
    }

    override fun getInputStream(): ServletInputStream {
        return CachedServletInputStream(cachedPayload)
    }

    override fun getReader(): BufferedReader {
        val byteArrayInputStream = ByteArrayInputStream(cachedPayload)
        return BufferedReader(InputStreamReader(byteArrayInputStream))
    }
}