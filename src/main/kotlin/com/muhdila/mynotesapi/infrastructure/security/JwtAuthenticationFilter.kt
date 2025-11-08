package com.muhdila.mynotesapi.infrastructure.security

import com.muhdila.mynotesapi.application.port.outbound.TokenProviderPort
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val tokenProvider: TokenProviderPort,
    private val userDetailsService: UserDetailsServiceImpl
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")
        if (header?.startsWith("Bearer ") == true) {
            val token = header.substringAfter("Bearer ").trim()
            if (tokenProvider.validateAccessToken(token)) {
                val userId = tokenProvider.getUserIdFromToken(token)
                val userDetails = userDetailsService.loadUserByUsername(userId)
                val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = auth
            } else {
                log.debug("Invalid or expired access token")
            }
        }
        filterChain.doFilter(request, response)
    }
}