package com.muhdila.mynotesapi.infrastructure.persistence.mongo

import com.muhdila.mynotesapi.application.port.outbound.UserRepositoryPort
import com.muhdila.mynotesapi.core.domain.User
import com.muhdila.mynotesapi.infrastructure.mapper.toDocument
import com.muhdila.mynotesapi.infrastructure.mapper.toDomain
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryMongo(
    private val repo: UserMongoRepository
) : UserRepositoryPort {
    override fun save(user: User): User = repo.save(user.toDocument()).toDomain()
    override fun findByEmail(email: String): User? = repo.findByEmail(email).map { it.toDomain() }.orElse(null)
    override fun findById(id: String): User? = repo.findById(id).map { it.toDomain() }.orElse(null)
    override fun existsByEmail(email: String): Boolean = repo.existsByEmail(email)
    override fun existsByUsername(username: String): Boolean = repo.existsByUsername(username)
}