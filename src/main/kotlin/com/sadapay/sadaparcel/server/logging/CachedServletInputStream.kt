package com.sadapay.sadaparcel.server.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream


class CachedServletInputStream(cachedBody: ByteArray?) : ServletInputStream() {
    private val cachedInputStream: InputStream

    init {
        cachedInputStream = ByteArrayInputStream(cachedBody)
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(CachedServletInputStream::class.java)
    }

    override fun isFinished(): Boolean {
        try {
            return cachedInputStream.available() == 0
        } catch (exp: IOException) {
            LOGGER.error(exp.message)
        }
        return false
    }

    override fun isReady(): Boolean {
        return true
    }

    override fun setReadListener(readListener: ReadListener?) {
        throw UnsupportedOperationException()
    }

    @Throws(IOException::class)
    override fun read(): Int {
        return cachedInputStream.read()
    }
}