package com.muhdila.mynotesapi.interfaces.api

import com.muhdila.mynotesapi.infrastructure.security.AppUserPrincipal
import org.springframework.security.core.context.SecurityContextHolder

object CurrentUser {
    fun id(): String {
        val auth = SecurityContextHolder.getContext().authentication
        val principal = auth?.principal
        return when (principal) {
            is AppUserPrincipal -> principal.id
            is org.springframework.security.core.userdetails.UserDetails -> principal.username // fallback
            is String -> principal
            else -> throw IllegalStateException("Unauthenticated")
        }
    }
}