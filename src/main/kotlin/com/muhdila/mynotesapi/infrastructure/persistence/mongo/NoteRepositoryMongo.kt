package com.muhdila.mynotesapi.infrastructure.persistence.mongo

import com.muhdila.mynotesapi.application.port.outbound.NoteRepositoryPort
import com.muhdila.mynotesapi.core.domain.Note
import com.muhdila.mynotesapi.infrastructure.mapper.toDomain
import com.muhdila.mynotesapi.infrastructure.mapper.toDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
class NoteRepositoryMongo(
    private val repo: NoteMongoRepository,
    private val queryRepo: NoteQueryRepository
) : NoteRepositoryPort {

    override fun save(note: Note): Note = repo.save(note.toDocument()).toDomain()

    override fun findByIdAndOwner(id: String, ownerId: String): Note? =
        repo.findByIdAndOwnerIdAndDeletedAtIsNull(id, ownerId)?.toDomain()

    override fun softDelete(note: Note) {
        repo.save(note.toDocument())
    }

    override fun search(
        ownerId: String, q: String?, tag: String?, archived: Boolean?, page: Int, size: Int, sort: String?
    ): Page<Note> {
        val pageable = buildPageable(page, size, sort)
        val docs = queryRepo.search(ownerId, q, tag, archived, pageable)
        return PageImpl(docs.content.map { it.toDomain() }, pageable, docs.totalElements)
    }

    private fun buildPageable(page: Int, size: Int, sort: String?): Pageable {
        val defaultSort = Sort.by(Sort.Order.desc("createdAt"))
        if (sort.isNullOrBlank()) return PageRequest.of(page, size, defaultSort)
        val parts = sort.split(",")
        val field = parts.getOrNull(0) ?: "createdAt"
        val direction = when (parts.getOrNull(1)?.lowercase()) { "asc" -> Sort.Direction.ASC; else -> Sort.Direction.DESC }
        return PageRequest.of(page, size, Sort.by(Sort.Order(direction, field)))
    }
}