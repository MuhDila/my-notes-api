package com.muhdila.mynotesapi.application.port.outbound

import com.muhdila.mynotesapi.core.domain.Note
import org.springframework.data.domain.Page

interface NoteRepositoryPort {
    fun save(note: Note): Note
    fun findByIdAndOwner(id: String, ownerId: String): Note?
    fun softDelete(note: Note)
    fun search(
        ownerId: String,
        q: String?,
        tag: String?,
        archived: Boolean?,
        page: Int,
        size: Int,
        sort: String?
    ): Page<Note>
}