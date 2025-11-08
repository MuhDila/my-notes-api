package com.muhdila.mynotesapi.infrastructure.persistence.mongo

import com.muhdila.mynotesapi.infrastructure.persistence.mongo.document.NoteDocument
import com.muhdila.mynotesapi.infrastructure.persistence.mongo.document.UserDocument
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserMongoRepository : MongoRepository<UserDocument, String> {
    fun findByEmail(email: String): Optional<UserDocument>
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
}

interface NoteMongoRepository : MongoRepository<NoteDocument, String> {
    fun findByIdAndOwnerIdAndDeletedAtIsNull(id: String, ownerId: String): NoteDocument?
}
