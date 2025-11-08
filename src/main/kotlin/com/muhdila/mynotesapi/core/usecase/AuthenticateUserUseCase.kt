package com.muhdila.mynotesapi.core.usecase

import com.muhdila.mynotesapi.application.port.outbound.PasswordHasherPort
import com.muhdila.mynotesapi.application.port.outbound.UserRepositoryPort
import com.muhdila.mynotesapi.core.domain.User
import com.muhdila.mynotesapi.core.domain.error.UnauthorizedException

class AuthenticateUserUseCase(
    private val userRepo: UserRepositoryPort,
    private val passwordHasher: PasswordHasherPort
) {
    data class Input(val email: String, val password: String)
    data class Output(val user: User)

    fun execute(input: Input): Output {
        val user = userRepo.findByEmail(input.email) ?: throw UnauthorizedException("Invalid credential")
        if (!passwordHasher.matches(input.password, user.password)) {
            throw UnauthorizedException("Invalid credential")
        }
        return Output(user)
    }
}