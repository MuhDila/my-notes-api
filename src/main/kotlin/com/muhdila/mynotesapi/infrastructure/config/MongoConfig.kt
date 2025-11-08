package com.muhdila.mynotesapi.infrastructure.config

import com.muhdila.mynotesapi.infrastructure.persistence.mongo.document.NoteDocument
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.Index
import org.springframework.data.mongodb.core.index.TextIndexDefinition
import org.springframework.data.domain.Sort

@Configuration
class MongoConfig {
    private val log = LoggerFactory.getLogger(MongoConfig::class.java)

    @Bean
    fun ensureIndexes(mongoTemplate: MongoTemplate) = ApplicationRunner {
        val noteOps = mongoTemplate.indexOps(NoteDocument::class.java)

        noteOps.createIndex(
            TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("title")
                .onField("content")
                .build()
        )

        noteOps.createIndex(Index().on("ownerId", Sort.Direction.ASC))
        noteOps.createIndex(Index().on("createdAt", Sort.Direction.DESC))
        noteOps.createIndex(Index().on("updatedAt", Sort.Direction.DESC))
        noteOps.createIndex(Index().on("deletedAt", Sort.Direction.ASC))

        log.info("Mongo indexes created for NoteDocument")
    }
}