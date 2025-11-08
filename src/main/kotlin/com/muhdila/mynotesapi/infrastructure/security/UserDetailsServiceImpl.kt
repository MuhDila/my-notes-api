package com.muhdila.mynotesapi.infrastructure.security

import com.muhdila.mynotesapi.application.port.outbound.UserRepositoryPort
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

data class AppUserPrincipal(
    val id: String,
    private val email: String,
    private val password: String,
    private val authoritiesSet: Set<GrantedAuthority>
) : UserDetails {
    override fun getAuthorities() = authoritiesSet
    override fun getPassword() = password
    override fun getUsername() = email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}

@Service
class UserDetailsServiceImpl(
    private val userRepo: UserRepositoryPort
) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val user = userRepo.findById(userId) ?: throw UsernameNotFoundException("User not found")
        val authorities = setOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
        return AppUserPrincipal(user.id!!, user.email, user.password, authorities)
    }
}