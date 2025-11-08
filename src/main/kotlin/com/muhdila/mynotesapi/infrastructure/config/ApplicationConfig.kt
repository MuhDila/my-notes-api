package com.muhdila.mynotesapi.infrastructure.config

import com.muhdila.mynotesapi.application.port.outbound.NoteRepositoryPort
import com.muhdila.mynotesapi.application.port.outbound.PasswordHasherPort
import com.muhdila.mynotesapi.application.port.outbound.TokenProviderPort
import com.muhdila.mynotesapi.application.port.outbound.UserRepositoryPort
import com.muhdila.mynotesapi.application.service.AuthService
import com.muhdila.mynotesapi.application.service.NoteService
import com.muhdila.mynotesapi.core.usecase.AuthenticateUserUseCase
import com.muhdila.mynotesapi.core.usecase.CreateNoteUseCase
import com.muhdila.mynotesapi.core.usecase.GetNoteUseCase
import com.muhdila.mynotesapi.core.usecase.RegisterUserUseCase
import com.muhdila.mynotesapi.core.usecase.SearchNotesUseCase
import com.muhdila.mynotesapi.core.usecase.SoftDeleteNoteUseCase
import com.muhdila.mynotesapi.core.usecase.UpdateNoteFullUseCase
import com.muhdila.mynotesapi.core.usecase.UpdateNotePartialUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {

    // === Auth ===
    @Bean
    fun registerUserUseCase(userRepo: UserRepositoryPort, hasher: PasswordHasherPort) =
        RegisterUserUseCase(userRepo, hasher)

    @Bean
    fun authenticateUserUseCase(userRepo: UserRepositoryPort, hasher: PasswordHasherPort) =
        AuthenticateUserUseCase(userRepo, hasher)

    @Bean
    fun authService(
        register: RegisterUserUseCase,
        authenticate: AuthenticateUserUseCase,
        tokenProvider: TokenProviderPort
    ) = AuthService(register, authenticate, tokenProvider)

    // === Notes ===
    @Bean fun createNoteUseCase(noteRepo: NoteRepositoryPort) = CreateNoteUseCase(noteRepo)
    @Bean fun getNoteUseCase(noteRepo: NoteRepositoryPort) = GetNoteUseCase(noteRepo)
    @Bean fun updateNoteFullUseCase(noteRepo: NoteRepositoryPort) = UpdateNoteFullUseCase(noteRepo)
    @Bean fun updateNotePartialUseCase(noteRepo: NoteRepositoryPort) = UpdateNotePartialUseCase(noteRepo)
    @Bean fun softDeleteNoteUseCase(noteRepo: NoteRepositoryPort) = SoftDeleteNoteUseCase(noteRepo)
    @Bean fun searchNotesUseCase(noteRepo: NoteRepositoryPort) = SearchNotesUseCase(noteRepo)

    @Bean
    fun noteService(
        create: CreateNoteUseCase,
        get: GetNoteUseCase,
        updateFull: UpdateNoteFullUseCase,
        updatePartial: UpdateNotePartialUseCase,
        softDelete: SoftDeleteNoteUseCase,
        search: SearchNotesUseCase
    ) = NoteService(create, get, updateFull, updatePartial, softDelete, search)
}