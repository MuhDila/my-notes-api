package com.muhdila.mynotesapi.infrastructure.persistence.mongo.document

import com.muhdila.mynotesapi.core.domain.UserRole
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("users")
data class UserDocument(
    @Id val id: String? = null,
    @Indexed(unique = true) val username: String,
    @Indexed(unique = true) val email: String,
    val password: String,
    val role: UserRole,
    val createdAt: Instant,
    val updatedAt: Instant
)