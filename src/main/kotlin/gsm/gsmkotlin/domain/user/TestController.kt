package gsm.gsmkotlin.domain.user

import gsm.gsmkotlin.domain.auth.entity.RefreshToken
import gsm.gsmkotlin.domain.auth.repository.RefreshTokenRepository
import gsm.gsmkotlin.domain.auth.service.ReissueTokenService
import gsm.gsmkotlin.global.security.jwt.TokenGenerator
import gsm.gsmkotlin.global.security.jwt.dto.TokenDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController (
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenGenerator: TokenGenerator,
    private val reissueTokenService: ReissueTokenService
) {
    @GetMapping("/login")
    fun login(@RequestParam userId: Long): TokenDto {
        val tokenDto = tokenGenerator.generateToken(userId.toString())
        refreshTokenRepository.save(RefreshToken(
            userId = userId,
            token = tokenDto.refreshToken,
            expirationTime = 10000
        ))
        return tokenDto
    }
    
    @GetMapping("/refresh")
    fun refresh(@RequestParam token: String): TokenDto =
        reissueTokenService.execute(token)
}
