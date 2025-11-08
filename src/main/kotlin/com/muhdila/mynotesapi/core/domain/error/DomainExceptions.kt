package com.muhdila.mynotesapi.core.domain.error

open class DomainExceptions(message: String): RuntimeException(message)
class NotFoundException(message: String): DomainExceptions(message)
class ConflictException(message: String): DomainExceptions(message)
class ForbiddenException(message: String): DomainExceptions(message)
class UnauthorizedException(message: String): DomainExceptions(message)