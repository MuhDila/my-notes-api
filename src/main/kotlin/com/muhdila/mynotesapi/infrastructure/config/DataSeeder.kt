package com.muhdila.mynotesapi.infrastructure.config

import com.muhdila.mynotesapi.application.port.outbound.PasswordHasherPort
import com.muhdila.mynotesapi.core.domain.UserRole
import com.muhdila.mynotesapi.infrastructure.persistence.mongo.NoteMongoRepository
import com.muhdila.mynotesapi.infrastructure.persistence.mongo.UserMongoRepository
import com.muhdila.mynotesapi.infrastructure.persistence.mongo.document.NoteDocument
import com.muhdila.mynotesapi.infrastructure.persistence.mongo.document.UserDocument
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.Instant

@Configuration
@Profile("dev")
class DataSeeder {
    private val log = LoggerFactory.getLogger(DataSeeder::class.java)

    @Bean
    fun seed(
        users: UserMongoRepository,
        notes: NoteMongoRepository,
        hasher: PasswordHasherPort
    ) = CommandLineRunner {
        val email = "demo@example.com"
        if (!users.existsByEmail(email)) {
            val now = Instant.now()
            val user = users.save(
                UserDocument(
                    username = "demo",
                    email = email,
                    password = hasher.hash("password"),
                    role = UserRole.USER,
                    createdAt = now,
                    updatedAt = now
                )
            )
            log.info("Seeded demo user: {} / password", user.email)

            repeat(3) { idx ->
                notes.save(
                    NoteDocument(
                        title = "Sample Note #$idx",
                        content = "Hello from seeded data ($idx)",
                        tags = listOf("demo", "sample"),
                        isArchived = false,
                        ownerId = user.id!!,
                        createdAt = now.plusSeconds(idx.toLong()),
                        updatedAt = now.plusSeconds(idx.toLong())
                    )
                )
            }
            log.info("Seeded notes for demo user")
        }
    }
}