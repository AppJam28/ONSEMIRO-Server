package com.example.gsmkotlin.global.security.auth

import com.example.gsmkotlin.domain.user.repository.UserRepository
import com.example.gsmkotlin.global.error.GlobalException
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails =
        CustomUserDetails(userRepository.findByEmail(email)
            ?: throw GlobalException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND))
}
