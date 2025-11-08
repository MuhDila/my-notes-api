package com.muhdila.mynotesapi.infrastructure.mapper

import com.muhdila.mynotesapi.core.domain.Note
import com.muhdila.mynotesapi.infrastructure.persistence.mongo.document.NoteDocument

fun Note.toDocument() = NoteDocument(
    id = id,
    title = title,
    content = content,
    tags = tags,
    isArchived = isArchived,
    ownerId = ownerId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun NoteDocument.toDomain() = Note(
    id = id,
    title = title,
    content = content,
    tags = tags,
    isArchived = isArchived,
    ownerId = ownerId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)