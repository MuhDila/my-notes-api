package com.muhdila.mynotesapi.core.usecase

import com.muhdila.mynotesapi.application.port.outbound.PasswordHasherPort
import com.muhdila.mynotesapi.application.port.outbound.UserRepositoryPort
import com.muhdila.mynotesapi.core.domain.User
import com.muhdila.mynotesapi.core.domain.UserRole
import com.muhdila.mynotesapi.core.domain.error.ConflictException
import java.time.Instant

class RegisterUserUseCase(
    private val userRepo: UserRepositoryPort,
    private val passwordHasher: PasswordHasherPort
) {
    data class Input(val username: String, val email: String, val password: String)
    data class Output(val user: User)

    fun execute(input: Input): Output {
        if (userRepo.existsByEmail(input.email)) throw ConflictException("Email already registered")
        if (userRepo.existsByUsername(input.username)) throw ConflictException("Username already registered")
        val now = Instant.now()
        val saved = userRepo.save(
            User(
                id = null,
                username = input.username,
                email = input.email,
                password = passwordHasher.hash(input.password),
                role = UserRole.USER,
                createdAt = now,
                updatedAt = now
            )
        )
        return Output(saved)
    }
}