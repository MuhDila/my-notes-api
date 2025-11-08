package com.muhdila.mynotesapi.interfaces.api

import com.muhdila.mynotesapi.application.service.NoteService
import com.muhdila.mynotesapi.core.domain.Note
import com.muhdila.mynotesapi.core.usecase.SearchNotesParams
import com.muhdila.mynotesapi.interfaces.api.dto.ApiResponse
import com.muhdila.mynotesapi.interfaces.api.dto.PageResponse
import com.muhdila.mynotesapi.interfaces.api.dto.notes.CreateNoteRequest
import com.muhdila.mynotesapi.interfaces.api.dto.notes.NoteResponse
import com.muhdila.mynotesapi.interfaces.api.dto.notes.PatchNoteRequest
import com.muhdila.mynotesapi.interfaces.api.dto.notes.UpdateNoteRequest
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/notes")
class NoteController(
    private val noteService: NoteService
) {

    @PostMapping
    fun create(@RequestBody @Valid body: CreateNoteRequest): ResponseEntity<ApiResponse<NoteResponse>> {
        val ownerId = CurrentUser.id()
        val note = noteService.create(ownerId, body.title, body.content, body.tags, body.isArchived)
        val resp = note.toResponse()
        return ResponseEntity.ok(ApiResponse(success = true, data = resp))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<ApiResponse<NoteResponse>> {
        val ownerId = CurrentUser.id()
        val note = noteService.getById(id, ownerId)
        return ResponseEntity.ok(ApiResponse(success = true, data = note.toResponse()))
    }

    @PutMapping("/{id}")
    fun updateFull(
        @PathVariable id: String,
        @RequestBody @Valid body: UpdateNoteRequest
    ): ResponseEntity<ApiResponse<NoteResponse>> {
        val ownerId = CurrentUser.id()
        val note = noteService.updateAll(id, ownerId, body.title, body.content, body.tags, body.isArchived)
        return ResponseEntity.ok(ApiResponse(success = true, data = note.toResponse()))
    }

    @PatchMapping("/{id}")
    fun updatePartial(
        @PathVariable id: String,
        @RequestBody body: PatchNoteRequest
    ): ResponseEntity<ApiResponse<NoteResponse>> {
        val ownerId = CurrentUser.id()
        val note = noteService.updateSome(id, ownerId, body.title, body.content, body.tags, body.isArchived)
        return ResponseEntity.ok(ApiResponse(success = true, data = note.toResponse()))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<ApiResponse<Unit>> {
        val ownerId = CurrentUser.id()
        noteService.deleteSoft(id, ownerId)
        return ResponseEntity.ok(ApiResponse(success = true, data = Unit))
    }

    @GetMapping
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) sort: String?,
        @RequestParam(required = false) q: String?,
        @RequestParam(required = false) tag: String?,
        @RequestParam(required = false) archived: Boolean?
    ): ResponseEntity<ApiResponse<PageResponse<NoteResponse>>> {
        val ownerId = CurrentUser.id()
        val result: Page<Note> = noteService.search(
            SearchNotesParams(ownerId, q, tag, archived, page, size, sort)
        )
        val items = result.content.map { it.toResponse() }
        val pr = PageResponse(
            items = items,
            page = result.number,
            size = result.size,
            totalItems = result.totalElements,
            totalPages = result.totalPages
        )
        return ResponseEntity.ok(ApiResponse(success = true, data = pr))
    }
}

// Mapping kecil untuk DTO response
private fun Note.toResponse() = NoteResponse(
    id = this.id!!,
    title = this.title,
    content = this.content,
    tags = this.tags,
    isArchived = this.isArchived,
    ownerId = this.ownerId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)