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