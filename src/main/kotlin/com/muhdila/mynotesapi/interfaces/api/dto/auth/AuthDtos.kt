package com.muhdila.mynotesapi.interfaces.api.dto.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank @field:Size(min = 3, max = 50)
    val username: String,
    @field:NotBlank @field:Email
    val email: String,
    @field:NotBlank @field:Size(min = 6, max = 100)
    val password: String
)

data class RegisterResponse(
    val userId: String,
    val username: String,
    val email: String
)

data class LoginRequest(
    @field:NotBlank @field:Email
    val email: String,
    @field:NotBlank
    val password: String
)

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String? = null,
    val expiresIn: Long
)

data class RefreshRequest(
    @field:NotBlank
    val refreshToken: String
)