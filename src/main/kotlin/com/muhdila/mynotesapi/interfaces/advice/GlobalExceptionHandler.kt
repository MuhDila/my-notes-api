package com.muhdila.mynotesapi.interfaces.advice

import com.muhdila.mynotesapi.core.domain.error.ConflictException
import com.muhdila.mynotesapi.core.domain.error.ForbiddenException
import com.muhdila.mynotesapi.core.domain.error.NotFoundException
import com.muhdila.mynotesapi.core.domain.error.UnauthorizedException
import com.muhdila.mynotesapi.interfaces.api.dto.ApiError
import com.muhdila.mynotesapi.interfaces.api.dto.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Unit>> {
        val errors = ex.bindingResult.fieldErrors.map {
            ApiError(code = "VALIDATION_ERROR", message = "${it.field} ${it.defaultMessage}", field = it.field)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse(success = false, errors = errors))
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(ex: UnauthorizedException) =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                ApiResponse<Unit>(
                    success = false,
                    errors = listOf(ApiError("UNAUTHORIZED", ex.message ?: "Unauthorized"))
                )
            )

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbidden(ex: ForbiddenException) =
        ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiResponse<Unit>(success = false, errors = listOf(ApiError("FORBIDDEN", ex.message ?: "Forbidden"))))

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException) =
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse<Unit>(success = false, errors = listOf(ApiError("NOT_FOUND", ex.message ?: "Not found"))))

    @ExceptionHandler(ConflictException::class, DuplicateKeyException::class)
    fun handleConflict(ex: Exception) =
        ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ApiResponse<Unit>(success = false, errors = listOf(ApiError("CONFLICT", ex.message ?: "Conflict"))))

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadReq(ex: IllegalArgumentException) =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse<Unit>(success = false, errors = listOf(ApiError("BAD_REQUEST", ex.message ?: "Bad request"))))

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ApiResponse<Unit>> {
        log.error("Unexpected error", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse(success = false, errors = listOf(ApiError("INTERNAL_ERROR", "Internal server error"))))
    }
}