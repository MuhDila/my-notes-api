package com.muhdila.mynotesapi.application.service

import com.muhdila.mynotesapi.application.port.outbound.TokenProviderPort
import com.muhdila.mynotesapi.core.domain.User
import com.muhdila.mynotesapi.core.domain.UserRole
import com.muhdila.mynotesapi.core.usecase.AuthenticateUserUseCase
import com.muhdila.mynotesapi.core.usecase.RegisterUserUseCase
import org.slf4j.LoggerFactory
import java.time.Instant

class AuthService(
    private val registerUserUseCase: RegisterUserUseCase,
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val tokenProvider: TokenProviderPort
) {
    private val log = LoggerFactory.getLogger(AuthService::class.java)

    data class Tokens(val accessToken: String, val refreshToken: String, val expiresIn: Long)

    fun register(username: String, email: String, password: String) =
        registerUserUseCase.execute(RegisterUserUseCase.Input(username, email, password)).user

    fun login(email: String, password: String, ): Tokens {
        val user = authenticateUserUseCase.execute(AuthenticateUserUseCase.Input(email, password)).user
        val access = tokenProvider.generateAccessToken(user)
        val refresh = tokenProvider.generateRefreshToken(user)
        log.debug("User {} logged in", user.id)
        return Tokens(access, refresh, tokenProvider.getExpiresInSecondsForAccess())
    }

    fun refresh(refreshToken: String): Tokens {
        if (!tokenProvider.validateRefreshToken(refreshToken)) {
            throw IllegalArgumentException("Invalid refresh token")
        }
        val userId = tokenProvider.getUserIdFromToken(refreshToken)
        // Pada sistem real, sebaiknya cek user masih aktif.
        // Di sini kita cukup generate accessToken baru.
        val access = tokenProvider.generateAccessToken(
            // User minimal hanya butuh id untuk claims; email/role bisa di-load jika diperlukan.
            User(
                id = userId,
                username = "n/a",
                email = "n/a",
                password = "n/a",
                role = UserRole.USER,
                createdAt = Instant.EPOCH,
                updatedAt = Instant.EPOCH
            )
        )
        return Tokens(access, refreshToken, tokenProvider.getExpiresInSecondsForAccess())
    }
}