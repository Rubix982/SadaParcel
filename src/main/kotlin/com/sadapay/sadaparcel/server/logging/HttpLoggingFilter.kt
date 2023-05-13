package com.sadapay.sadaparcel.server.logging

import org.apache.commons.io.output.TeeOutputStream
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.*
import java.util.*
import javax.servlet.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse

@Component
@Order(1)
class HttpLoggingFilter : Filter {
    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest, response: ServletResponse,
        chain: FilterChain
    ) {
        try {
            val httpServletRequest = request as HttpServletRequest
            val httpServletResponse = response as HttpServletResponse
            val requestMap = getTypesafeRequestMap(httpServletRequest)
            val bufferedRequest = BufferedRequestWrapper(
                httpServletRequest
            )
            val bufferedResponse = BufferedResponseWrapper(
                httpServletResponse
            )
            val logMessage = StringBuilder(
                "REST Request - "
            ).append("[HTTP METHOD:")
                .append(httpServletRequest.method)
                .append("] [PATH INFO:")
                .append(httpServletRequest.servletPath)
                .append("] [REQUEST PARAMETERS:").append(requestMap)
                .append("] [REQUEST BODY:")
                .append(bufferedRequest.requestBody)
                .append("] [REMOTE ADDRESS:")
                .append(httpServletRequest.remoteAddr).append("]")
            chain.doFilter(bufferedRequest, bufferedResponse)
            logMessage.append(" [RESPONSE:")
                .append(bufferedResponse.content).append("]")
            log.debug(logMessage.toString())
        } catch (a: Throwable) {
            log.error(a.message)
        }
    }

    private fun getTypesafeRequestMap(request: HttpServletRequest): Map<String, String> {
        val typesafeRequestMap: MutableMap<String, String> = HashMap()
        val requestParamNames: Enumeration<*> = request.parameterNames
        while (requestParamNames.hasMoreElements()) {
            val requestParamName = requestParamNames.nextElement() as String
            val requestParamValue: String = if (requestParamName.equals("password", ignoreCase = true)) {
                "********"
            } else {
                request.getParameter(requestParamName)
            }
            typesafeRequestMap[requestParamName] = requestParamValue
        }
        return typesafeRequestMap
    }

    override fun destroy() {}
    private class BufferedRequestWrapper(req: HttpServletRequest) : HttpServletRequestWrapper(req) {
        private var byteArrayInputStream: ByteArrayInputStream? = null
        private var byteArrayOutputStream: ByteArrayOutputStream = ByteArrayOutputStream()
        private var bufferedServletInputStream: BufferedServletInputStream? = null
        private var buffer: ByteArray? = null

        init {
            // Read InputStream and store its content in a buffer.
            val `is`: InputStream = req.inputStream
            val buf = ByteArray(1024)
            var read: Int
            while (`is`.read(buf).also { read = it } > 0) {
                byteArrayOutputStream.write(buf, 0, read)
            }
            buffer = byteArrayOutputStream.toByteArray()
        }

        override fun getInputStream(): ServletInputStream {
            byteArrayInputStream = ByteArrayInputStream(buffer)
            bufferedServletInputStream = BufferedServletInputStream(byteArrayInputStream)
            return bufferedServletInputStream!!
        }

        @get:Throws(IOException::class)
        val requestBody: String
            get() {
                val reader = BufferedReader(
                    InputStreamReader(
                        this.inputStream
                    )
                )
                var line: String?
                val inputBuffer = StringBuilder()
                do {
                    line = reader.readLine()
                    if (null != line) {
                        inputBuffer.append(line.trim { it <= ' ' })
                    }
                } while (line != null)
                reader.close()
                return inputBuffer.toString().trim { it <= ' ' }
            }
    }

    private class BufferedServletInputStream(private val byteArrayInputStream: ByteArrayInputStream?) :
        ServletInputStream() {
        override fun available(): Int {
            return byteArrayInputStream!!.available()
        }

        override fun read(): Int {
            return byteArrayInputStream!!.read()
        }

        override fun read(buf: ByteArray, off: Int, len: Int): Int {
            return byteArrayInputStream!!.read(buf, off, len)
        }

        override fun isFinished(): Boolean {
            return false
        }

        override fun isReady(): Boolean {
            return true
        }

        override fun setReadListener(readListener: ReadListener) {}
    }

    inner class TeeServletOutputStream(one: OutputStream?, two: OutputStream?) :
        ServletOutputStream() {
        private val targetStream: TeeOutputStream

        init {
            targetStream = TeeOutputStream(one, two)
        }

        @Throws(IOException::class)
        override fun write(arg0: Int) {
            targetStream.write(arg0)
        }

        @Throws(IOException::class)
        override fun flush() {
            super.flush()
            targetStream.flush()
        }

        @Throws(IOException::class)
        override fun close() {
            super.close()
            targetStream.close()
        }

        override fun isReady(): Boolean {
            return false
        }

        override fun setWriteListener(writeListener: WriteListener) {}
    }

    inner class BufferedResponseWrapper(private var original: HttpServletResponse) : HttpServletResponse {
        private var tee: TeeServletOutputStream? = null
        private var bos: ByteArrayOutputStream? = null

        val content: String
            get() = bos.toString()

        @Throws(IOException::class)
        override fun getWriter(): PrintWriter {
            return original.writer
        }

        @Throws(IOException::class)
        override fun getOutputStream(): ServletOutputStream {
            if (tee == null) {
                bos = ByteArrayOutputStream()
                tee = TeeServletOutputStream(
                    original.outputStream,
                    bos
                )
            }
            return tee!!
        }

        override fun getCharacterEncoding(): String {
            return original.characterEncoding
        }

        override fun getContentType(): String {
            return original.contentType
        }

        override fun setCharacterEncoding(charset: String) {
            original.characterEncoding = charset
        }

        override fun setContentLength(len: Int) {
            original.setContentLength(len)
        }

        override fun setContentLengthLong(l: Long) {
            original.setContentLengthLong(l)
        }

        override fun setContentType(type: String) {
            original.contentType = type
        }

        override fun setBufferSize(size: Int) {
            original.bufferSize = size
        }

        override fun getBufferSize(): Int {
            return original.bufferSize
        }

        @Throws(IOException::class)
        override fun flushBuffer() {
            tee!!.flush()
        }

        override fun resetBuffer() {
            original.resetBuffer()
        }

        override fun isCommitted(): Boolean {
            return original.isCommitted
        }

        override fun reset() {
            original.reset()
        }

        override fun setLocale(loc: Locale) {
            original.locale = loc
        }

        override fun getLocale(): Locale {
            return original.locale
        }

        override fun addCookie(cookie: Cookie) {
            original.addCookie(cookie)
        }

        override fun containsHeader(name: String): Boolean {
            return original.containsHeader(name)
        }

        override fun encodeURL(url: String): String {
            return original.encodeURL(url)
        }

        override fun encodeRedirectURL(url: String): String {
            return original.encodeRedirectURL(url)
        }

        @Deprecated("Deprecated in Java")
        override fun encodeUrl(url: String): String {
            return original.encodeUrl(url)
        }

        @Deprecated("Deprecated in Java")
        override fun encodeRedirectUrl(url: String): String {
            return original.encodeRedirectUrl(url)
        }

        @Deprecated("Deprecated in Java")
        override fun setStatus(sc: Int, sm: String) {
            original.setStatus(sc, sm)
        }

        @Throws(IOException::class)
        override fun sendError(sc: Int, msg: String) {
            original.sendError(sc, msg)
        }

        @Throws(IOException::class)
        override fun sendError(sc: Int) {
            original.sendError(sc)
        }

        @Throws(IOException::class)
        override fun sendRedirect(location: String) {
            original.sendRedirect(location)
        }

        override fun setDateHeader(name: String, date: Long) {
            original.setDateHeader(name, date)
        }

        override fun addDateHeader(name: String, date: Long) {
            original.addDateHeader(name, date)
        }

        override fun setHeader(name: String, value: String) {
            original.setHeader(name, value)
        }

        override fun addHeader(name: String, value: String) {
            original.addHeader(name, value)
        }

        override fun setIntHeader(name: String, value: Int) {
            original.setIntHeader(name, value)
        }

        override fun addIntHeader(name: String, value: Int) {
            original.addIntHeader(name, value)
        }

        override fun setStatus(sc: Int) {
            original.status = sc
        }

        override fun getHeader(arg0: String): String {
            return original.getHeader(arg0)
        }

        override fun getHeaderNames(): Collection<String> {
            return original.headerNames
        }

        override fun getHeaders(arg0: String): Collection<String> {
            return original.getHeaders(arg0)
        }

        override fun getStatus(): Int {
            return original.status
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(HttpLoggingFilter::class.java)
    }
}