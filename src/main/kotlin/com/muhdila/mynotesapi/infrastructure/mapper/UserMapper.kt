package com.muhdila.mynotesapi.infrastructure.mapper

import com.muhdila.mynotesapi.core.domain.User
import com.muhdila.mynotesapi.infrastructure.persistence.mongo.document.UserDocument

fun User.toDocument() = UserDocument(
    id = id,
    username = username,
    email = email,
    password = password,
    role = role,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun UserDocument.toDomain() = User(
    id = id,
    username = username,
    email = email,
    password = password,
    role = role,
    createdAt = createdAt,
    updatedAt = updatedAt
)