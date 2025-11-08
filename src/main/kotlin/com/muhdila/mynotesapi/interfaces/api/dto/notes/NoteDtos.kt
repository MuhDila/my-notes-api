package com.muhdila.mynotesapi.interfaces.api.dto.notes

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.Instant

data class CreateNoteRequest(
    @field:NotBlank @field:Size(max = 200)
    val title: String,
    @field:NotBlank
    val content: String,
    val tags: List<String> = emptyList(),
    val isArchived: Boolean = false
)

data class UpdateNoteRequest(
    @field:NotBlank @field:Size(max = 200)
    val title: String,
    @field:NotBlank
    val content: String,
    val tags: List<String> = emptyList(),
    val isArchived: Boolean = false
)

data class PatchNoteRequest(
    val title: String? = null,
    val content: String? = null,
    val tags: List<String>? = null,
    val isArchived: Boolean? = null
)

data class NoteResponse(
    val id: String,
    val title: String,
    val content: String,
    val tags: List<String>,
    val isArchived: Boolean,
    val ownerId: String,
    val createdAt: Instant,
    val updatedAt: Instant
)