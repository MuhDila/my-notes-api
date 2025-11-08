package com.muhdila.mynotesapi.interfaces.api

import com.muhdila.mynotesapi.application.service.AuthService
import com.muhdila.mynotesapi.interfaces.api.dto.ApiResponse
import com.muhdila.mynotesapi.interfaces.api.dto.auth.LoginRequest
import com.muhdila.mynotesapi.interfaces.api.dto.auth.RefreshRequest
import com.muhdila.mynotesapi.interfaces.api.dto.auth.RegisterRequest
import com.muhdila.mynotesapi.interfaces.api.dto.auth.RegisterResponse
import com.muhdila.mynotesapi.interfaces.api.dto.auth.TokenResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody @Valid body: RegisterRequest): ResponseEntity<ApiResponse<RegisterResponse>> {
        val user = authService.register(body.username, body.email, body.password)
        val resp = RegisterResponse(userId = user.id!!, username = user.username, email = user.email)
        return ResponseEntity.ok(ApiResponse(success = true, data = resp))
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid body: LoginRequest): ResponseEntity<ApiResponse<TokenResponse>> {
        val tokens = authService.login(body.email, body.password)
        val resp = TokenResponse(accessToken = tokens.accessToken, refreshToken = tokens.refreshToken, expiresIn = tokens.expiresIn)
        return ResponseEntity.ok(ApiResponse(success = true, data = resp))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody @Valid body: RefreshRequest): ResponseEntity<ApiResponse<TokenResponse>> {
        val tokens = authService.refresh(body.refreshToken)
        val resp = TokenResponse(accessToken = tokens.accessToken, refreshToken = null, expiresIn = tokens.expiresIn)
        return ResponseEntity.ok(ApiResponse(success = true, data = resp))
    }
}