package com.muhdila.mynotesapi.application.port.outbound

interface PasswordHasherPort {
    fun hash(raw: String): String
    fun matches(raw: String, hashed: String): Boolean
}