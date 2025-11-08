package com.muhdila.mynotesapi.core.domain

import java.time.Instant

data class Note(
    val id: String?,
    val title: String,
    val content: String,
    val tags: List<String>,
    val isArchived: Boolean,
    val ownerId: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null
)