package com.muhdila.mynotesapi.interfaces.api.dto

import java.time.Instant

data class ApiError(
    val code: String,
    val message: String,
    val field: String? = null
)

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val errors: List<ApiError>? = null,
    val timestamp: Instant = Instant.now()
)

data class PageResponse<T>(
    val items: List<T>,
    val page: Int,
    val size: Int,
    val totalItems: Long,
    val totalPages: Int
)