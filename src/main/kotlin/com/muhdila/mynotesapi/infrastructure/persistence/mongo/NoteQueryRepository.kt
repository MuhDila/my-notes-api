package com.muhdila.mynotesapi.infrastructure.persistence.mongo

import com.muhdila.mynotesapi.infrastructure.persistence.mongo.document.NoteDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.stereotype.Repository

@Repository
class NoteQueryRepository(
    private val mongoTemplate: MongoTemplate
) {
    fun search(
        ownerId: String,
        q: String?,
        tag: String?,
        archived: Boolean?,
        pageable: Pageable
    ): Page<NoteDocument> {
        val criteriaList = mutableListOf<Criteria>()
        criteriaList += Criteria.where("ownerId").`is`(ownerId)
        criteriaList += Criteria.where("deletedAt").`is`(null)
        if (archived != null) criteriaList += Criteria.where("isArchived").`is`(archived)
        if (!tag.isNullOrBlank()) criteriaList += Criteria.where("tags").`in`(listOf(tag))

        val query = Query()
        if (!q.isNullOrBlank()) {
            val text = TextCriteria.forDefaultLanguage().matching(q)
            query.addCriteria(text)
        }
        if (criteriaList.isNotEmpty()) {
            query.addCriteria(Criteria().andOperator(*criteriaList.toTypedArray()))
        }
        query.with(pageable)

        val results = mongoTemplate.find(query, NoteDocument::class.java)
        val count = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), NoteDocument::class.java)
        return PageImpl(results, pageable, count)
    }
}
