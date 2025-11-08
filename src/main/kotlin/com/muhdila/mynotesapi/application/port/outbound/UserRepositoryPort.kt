package com.muhdila.mynotesapi.application.port.outbound

import com.muhdila.mynotesapi.core.domain.User

interface UserRepositoryPort {
    fun save(user: User): User
    fun findByEmail(email: String): User?
    fun findById(id: String): User?
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
}