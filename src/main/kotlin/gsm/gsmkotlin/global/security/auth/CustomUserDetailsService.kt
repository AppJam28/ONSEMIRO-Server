package gsm.gsmkotlin.global.security.auth

import gsm.gsmkotlin.domain.user.repository.UserRepository
import gsm.gsmkotlin.global.error.GlobalException
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails =
        CustomUserDetails(
            userRepository.findById(userId.toLong())
                .orElseThrow { GlobalException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }
        )
}
