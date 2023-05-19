package com.sadapay.sadaparcel.config

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class SadaParcelSecurityConfiguration {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.requestMatcher(EndpointRequest.toAnyEndpoint())
            .authorizeHttpRequests { requests -> requests.anyRequest().permitAll() }
        http.authorizeRequests { requests -> requests.anyRequest().authenticated() }
        return http.build()
    }
}

