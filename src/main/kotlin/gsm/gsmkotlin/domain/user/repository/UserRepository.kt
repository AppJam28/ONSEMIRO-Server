package gsm.gsmkotlin.domain.user.repository

import gsm.gsmkotlin.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
}
