package com.muhdila.mynotesapi.core.usecase

import com.muhdila.mynotesapi.application.port.outbound.NoteRepositoryPort
import com.muhdila.mynotesapi.core.domain.Note
import com.muhdila.mynotesapi.core.domain.error.NotFoundException
import java.time.Instant

class CreateNoteUseCase(
    private val noteRepo: NoteRepositoryPort
) {
    data class Input(
        val title: String,
        val content: String,
        val tags: List<String>,
        val isArchived: Boolean,
        val ownerId: String
    )

    fun execute(input: Input): Note {
        val now = Instant.now()
        return noteRepo.save(
            Note(
                id = null,
                title = input.title,
                content = input.content,
                tags = input.tags,
                isArchived = input.isArchived,
                ownerId = input.ownerId,
                createdAt = now,
                updatedAt = now,
                deletedAt = null
            )
        )
    }
}

class GetNoteUseCase(
    private val noteRepo: NoteRepositoryPort
) {
    fun execute(id: String, ownerId: String): Note {
        val note = noteRepo.findByIdAndOwner(id, ownerId) ?: throw NotFoundException("Note not found")
        return note
    }
}

class UpdateNoteFullUseCase(
    private val noteRepo: NoteRepositoryPort
) {
    data class Input(
        val id: String,
        val ownerId: String,
        val title: String,
        val content: String,
        val tags: List<String>,
        val isArchived: Boolean,
    )

    fun execute(input: Input): Note {
        val existing = noteRepo.findByIdAndOwner(input.id, input.ownerId) ?: throw NotFoundException("Note not found")
        val updated = existing.copy(
            title = input.title,
            content = input.content,
            tags = input.tags,
            isArchived = input.isArchived,
            updatedAt = Instant.now(),
        )
        return noteRepo.save(updated)
    }
}

class UpdateNotePartialUseCase(
    private val noteRepo: NoteRepositoryPort
) {
    data class Input(
        val id: String,
        val ownerId: String,
        val title: String?,
        val content: String?,
        val tags: List<String>?,
        val isArchived: Boolean?,
    )

    fun execute(input: Input): Note {
        val existing = noteRepo.findByIdAndOwner(input.id, input.ownerId) ?: throw NotFoundException("Note not found")
        val updated = existing.copy(
            title = input.title ?: existing.title,
            content = input.content ?: existing.content,
            tags = input.tags ?: existing.tags,
            isArchived = input.isArchived ?: existing.isArchived,
            updatedAt = Instant.now()
        )
        return noteRepo.save(updated)
    }
}

class SoftDeleteNoteUseCase(
    private val noteRepo: NoteRepositoryPort
) {
    fun execute(id: String, ownerId: String) {
        val existing = noteRepo.findByIdAndOwner(id, ownerId) ?: throw NotFoundException("Note not found")
        noteRepo.softDelete(existing.copy(deletedAt = Instant.now(), updatedAt = Instant.now()))
    }
}

data class SearchNotesParams(
    val ownerId: String,
    val q: String?,
    val tag: String?,
    val archived: Boolean?,
    val page: Int,
    val size: Int,
    val sort: String?
)

class SearchNotesUseCase(
    private val noteRepo: NoteRepositoryPort
) {
    fun execute(params: SearchNotesParams) =
        noteRepo.search(
            ownerId = params.ownerId,
            q = params.q,
            tag = params.tag,
            archived = params.archived,
            page = params.page,
            size = params.size,
            sort = params.sort
        )
}