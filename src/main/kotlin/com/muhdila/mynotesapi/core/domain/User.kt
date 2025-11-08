package com.muhdila.mynotesapi.core.domain

import java.time.Instant

data class User(
    val id: String?,
    val username: String,
    val email: String,
    val password: String,
    val role: UserRole = UserRole.USER,
    val createdAt: Instant,
    val updatedAt: Instant
)

enum class UserRole { USER, ADMIN }