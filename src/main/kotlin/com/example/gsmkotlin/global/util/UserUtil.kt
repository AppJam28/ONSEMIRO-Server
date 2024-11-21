package com.example.gsmkotlin.global.util

import com.example.gsmkotlin.domain.user.entity.User
import com.example.gsmkotlin.domain.user.repository.UserRepository
import com.example.gsmkotlin.global.error.GlobalException
import com.example.gsmkotlin.global.security.auth.CustomUserDetails
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserUtil (
    private val userRepository: UserRepository
) {
    fun getCurrentUser(): User {
        val principal = SecurityContextHolder.getContext().authentication.principal
        
        if (principal is CustomUserDetails) {
            val email = principal.username
            return userRepository.findByEmail(email)
                ?: throw GlobalException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
        } else {
            throw GlobalException("현재 인증되어 있는 유저의 principal이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED)
        }
    }
}
