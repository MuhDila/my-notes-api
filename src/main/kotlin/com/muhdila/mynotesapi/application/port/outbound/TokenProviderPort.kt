package com.muhdila.mynotesapi.application.port.outbound

import com.muhdila.mynotesapi.core.domain.User

interface TokenProviderPort {
    fun generateAccessToken(user: User): String
    fun generateRefreshToken(user: User): String
    fun validateAccessToken(token: String): Boolean
    fun validateRefreshToken(token: String): Boolean
    fun getUserIdFromToken(token: String): String
    fun getExpiresInSecondsForAccess(): Long
}