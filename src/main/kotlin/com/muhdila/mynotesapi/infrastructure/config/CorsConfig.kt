package com.muhdila.mynotesapi.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig(
    @Value("\${cors.allowed-origins:*}") private val allowedOrigins: String
) {
    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true
        val origins = allowedOrigins.split(",").map { it.trim() }
        config.allowedOrigins = origins
        config.allowedHeaders = listOf("*")
        config.allowedMethods = listOf("GET","POST","PUT","PATCH","DELETE","OPTIONS")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}