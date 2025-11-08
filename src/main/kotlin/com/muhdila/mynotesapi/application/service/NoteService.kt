package com.muhdila.mynotesapi.application.service

import com.muhdila.mynotesapi.core.domain.Note
import com.muhdila.mynotesapi.core.usecase.CreateNoteUseCase
import com.muhdila.mynotesapi.core.usecase.GetNoteUseCase
import com.muhdila.mynotesapi.core.usecase.SearchNotesParams
import com.muhdila.mynotesapi.core.usecase.SearchNotesUseCase
import com.muhdila.mynotesapi.core.usecase.SoftDeleteNoteUseCase
import com.muhdila.mynotesapi.core.usecase.UpdateNoteFullUseCase
import com.muhdila.mynotesapi.core.usecase.UpdateNotePartialUseCase
import org.springframework.data.domain.Page

class NoteService(
    private val createNote: CreateNoteUseCase,
    private val getNote: GetNoteUseCase,
    private val updateFull: UpdateNoteFullUseCase,
    private val updatePartial: UpdateNotePartialUseCase,
    private val softDelete: SoftDeleteNoteUseCase,
    private val searchNotes: SearchNotesUseCase,
) {
    fun create(
        ownerId: String,
        title: String,
        content: String,
        tags: List<String>,
        isArchived: Boolean
    ) = createNote.execute(CreateNoteUseCase.Input(title, content, tags, isArchived, ownerId))

    fun updateAll(
        id: String,
        ownerId: String,
        title: String,
        content: String,
        tags: List<String>,
        isArchived: Boolean
    ) = updateFull.execute(UpdateNoteFullUseCase.Input(id, ownerId, title, content, tags, isArchived))

    fun updateSome(
        id: String,
        ownerId: String,
        title: String?,
        content: String?,
        tags: List<String>?,
        isArchived: Boolean?
    ) = updatePartial.execute(UpdateNotePartialUseCase.Input(id, ownerId, title, content, tags, isArchived))

    fun getById(id: String, ownerId: String) = getNote.execute(id, ownerId)

    fun deleteSoft(id: String, ownerId: String) = softDelete.execute(id, ownerId)

    fun search(params: SearchNotesParams): Page<Note> = searchNotes.execute(params)
}