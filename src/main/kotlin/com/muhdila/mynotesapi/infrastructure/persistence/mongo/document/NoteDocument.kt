package com.muhdila.mynotesapi.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("notes")
@CompoundIndexes(
    value = [
        CompoundIndex(def = "{ 'ownerId': 1, 'createdAt': -1 }"),
        CompoundIndex(def = "{ 'ownerId': 1, 'updatedAt': -1 }")
    ]
)
data class NoteDocument(
    @Id val id: String? = null,
    @TextIndexed val title: String,
    @TextIndexed val content: String,
    val tags: List<String>,
    @Indexed val isArchived: Boolean,
    @Indexed val ownerId: String,
    @Indexed val createdAt: Instant,
    @Indexed val updatedAt: Instant,
    @Indexed val deletedAt: Instant? = null
)
