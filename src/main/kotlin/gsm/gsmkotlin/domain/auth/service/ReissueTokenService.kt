package gsm.gsmkotlin.domain.auth.service

import gsm.gsmkotlin.domain.auth.entity.RefreshToken
import gsm.gsmkotlin.domain.auth.repository.RefreshTokenRepository
import gsm.gsmkotlin.domain.user.repository.UserRepository
import gsm.gsmkotlin.global.error.GlobalException
import gsm.gsmkotlin.global.filter.JwtReqFilter.Companion.BEARER_PREFIX
import gsm.gsmkotlin.global.security.jwt.TokenGenerator
import gsm.gsmkotlin.global.security.jwt.dto.TokenDto
import gsm.gsmkotlin.global.security.jwt.properties.JwtEnvironment
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReissueTokenService(
    private val tokenGenerator: TokenGenerator,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtEnv: JwtEnvironment
) {
    @Transactional
    fun execute(token: String?): TokenDto {
        isNotNullRefreshToken(token)
        
        val removePrefixToken: String = token!!.replace(BEARER_PREFIX, "")
        val refreshToken = refreshTokenRepository.findByToken(removePrefixToken)
            ?: throw GlobalException("존재하지 않는 refresh token 입니다.",HttpStatus.NOT_FOUND)
        
        val userId = tokenGenerator.getUserIdFromRefreshToken(refreshToken.token)
        isExistsUser(userId)
        
        val tokenDto = tokenGenerator.generateToken(userId)
        saveNewRefreshToken(tokenDto.refreshToken, refreshToken.userId)
        return tokenDto
    }
    
    private fun isNotNullRefreshToken(token: String?) {
        if (token == null) throw GlobalException("refresh token을 요청 헤더에 포함시켜 주세요.", HttpStatus.BAD_REQUEST)
    }
    
    private fun isExistsUser(userId: String) {
        if (!userRepository.existsById(userId.toLong())) throw GlobalException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
    }
    
    private fun saveNewRefreshToken(token: String, userId: Long) {
        val refreshToken = RefreshToken(
            userId = userId,
            token = token,
            expirationTime = jwtEnv.refreshExp
        )
        
        refreshTokenRepository.save(refreshToken)
    }
    
}
