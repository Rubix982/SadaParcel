package com.sadapay.sadaparcel.config

import com.sadapay.sadaparcel.server.logging.HttpLoggingFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class HttpLoggingFilterRegistrationBean {
    @Bean
    fun filterRegistrationBean(): FilterRegistrationBean<HttpLoggingFilter?>? {
        val registrationBean = FilterRegistrationBean<HttpLoggingFilter?>()
        val customURLFilter = HttpLoggingFilter()
        registrationBean.filter = customURLFilter
        registrationBean.addUrlPatterns("*")
        registrationBean.order = 1 // Set precedence
        return registrationBean
    }
}
