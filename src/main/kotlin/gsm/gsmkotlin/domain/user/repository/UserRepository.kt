package gsm.gsmkotlin.domain.user.repository

import gsm.gsmkotlin.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>
