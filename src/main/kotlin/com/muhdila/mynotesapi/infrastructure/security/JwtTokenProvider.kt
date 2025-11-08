package com.muhdila.mynotesapi.infrastructure.security

import com.muhdila.mynotesapi.application.port.outbound.TokenProviderPort
import com.muhdila.mynotesapi.core.domain.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtProps {
    lateinit var secret: String
    var accessExpirationMillis: Long = 3600000
    var refreshExpirationMillis: Long = 604800000
}

@Component
class JwtTokenProvider(
    private val props: JwtProps
) : TokenProviderPort {

    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(props.secret.toByteArray(StandardCharsets.UTF_8))
    }

    override fun generateAccessToken(user: User): String {
        val now = Date()
        val expiry = Date(now.time + props.accessExpirationMillis)
        return Jwts.builder()
            .subject(user.id ?: "unknown")
            .issuedAt(now)
            .expiration(expiry)
            .claim("email", user.email)
            .claim("role", user.role.name)
            .claim("type", "ACCESS")
            .signWith(key)
            .compact()
    }

    override fun generateRefreshToken(user: User): String {
        val now = Date()
        val expiry = Date(now.time + props.refreshExpirationMillis)
        return Jwts.builder()
            .subject(user.id ?: "unknown")
            .issuedAt(now)
            .expiration(expiry)
            .claim("type", "REFRESH")
            .signWith(key)
            .compact()
    }

    override fun validateAccessToken(token: String): Boolean = validate(token, "ACCESS")
    override fun validateRefreshToken(token: String): Boolean = validate(token, "REFRESH")

    private fun validate(token: String, expectedType: String): Boolean =
        try {
            val claims = parseClaims(token)
            val type = claims["type"] as? String
            type == expectedType && !isExpired(claims)
        } catch (_: Exception) {
            false
        }

    override fun getUserIdFromToken(token: String): String {
        val claims = parseClaims(token)
        return claims.subject
    }

    private fun parseClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

    private fun isExpired(claims: Claims) = claims.expiration.before(Date())

    override fun getExpiresInSecondsForAccess(): Long = props.accessExpirationMillis / 1000
}