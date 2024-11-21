package com.example.gsmkotlin.domain.user.repository

import com.example.gsmkotlin.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
