package gsm.gsmkotlin.domain.user.service

import gsm.gsmkotlin.domain.user.dto.req.RegisterReqDto
import gsm.gsmkotlin.domain.user.entity.User
import gsm.gsmkotlin.domain.user.repository.UserRepository
import gsm.gsmkotlin.global.util.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userUtil: UserUtil
    
) {
    fun register(registerReqDto: RegisterReqDto): Unit {
        
        val password = passwordEncoder.encode(registerReqDto.password)
        
        if(userRepository.findByEmail(registerReqDto.email).isPresent)
            throw IllegalArgumentException("이미 가입된 유저입니다.")
        
        val user: User = User(
            email = registerReqDto.email,
            password = password,
            name = registerReqDto.name,
            role = registerReqDto.role
        )
        userRepository.save(user)
    }
}
