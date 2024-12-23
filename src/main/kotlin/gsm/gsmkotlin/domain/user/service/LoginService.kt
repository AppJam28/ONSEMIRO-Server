package gsm.gsmkotlin.domain.user.service

import gsm.gsmkotlin.domain.auth.entity.RefreshToken
import gsm.gsmkotlin.domain.auth.repository.RefreshTokenRepository
import gsm.gsmkotlin.domain.user.dto.req.LoginServiceReqDto
import gsm.gsmkotlin.domain.user.repository.UserRepository
import gsm.gsmkotlin.global.security.jwt.TokenGenerator
import gsm.gsmkotlin.global.security.jwt.dto.TokenDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val tokenGenerator: TokenGenerator,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    fun logIn(loginServiceReqDto: LoginServiceReqDto): TokenDto {
        // 이메일로 유저 찾기
        val user = userRepository.findByEmail(loginServiceReqDto.email)
            .orElseThrow { IllegalArgumentException("${loginServiceReqDto.email}이라는 이메일을 찾을 수 없습니다.") }
        
        // 입력된 비밀번호와 저장된 비밀번호 비교
        if (!passwordEncoder.matches(loginServiceReqDto.password, user.password)) {
            throw IllegalArgumentException("올바르지 정보입니다.")
        }
        
        // 토큰 생성
        val token: TokenDto = tokenGenerator.generateToken(user.email)
        
        // 리프레시 토큰 생성 및 저장
        val refreshToken: RefreshToken = RefreshToken(
            userId = user.id,
            token = token.accessToken,
            expirationTime = 60000000  // 리프레시 토큰 만료 시간
        )
        
        refreshTokenRepository.save(refreshToken)
        
        // 생성된 토큰 반환
        return token
    }
}
