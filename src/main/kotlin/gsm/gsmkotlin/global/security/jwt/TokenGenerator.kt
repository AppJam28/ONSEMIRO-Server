package gsm.gsmkotlin.global.security.jwt

import gsm.gsmkotlin.global.security.jwt.dto.TokenDto
import gsm.gsmkotlin.global.security.jwt.properties.JwtEnvironment
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class TokenGenerator(
    private val jwtEnv: JwtEnvironment
) {
    
    private final val TOKEN_TYPE = "tokenType"
    private final val ACCESS_TOKEN = "accessToken"
    private final val REFRESH_TOKEN = "refreshToken"
    
    fun generateToken(email: String): TokenDto =
        TokenDto(
            accessToken = generateAccessToken(email),
            refreshToken = generateRefreshToken(email),
            accessTokenExp = jwtEnv.accessExp,
            refreshTokenExp = jwtEnv.refreshExp
        )
    
    private fun generateAccessToken(email: String): String =
        Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtEnv.accessSecret.toByteArray(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
            .setSubject(email)
            .claim(TOKEN_TYPE, ACCESS_TOKEN)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtEnv.accessExp * 1000))
            .compact()
    
    private fun generateRefreshToken(email: String): String =
        Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtEnv.refreshSecret.toByteArray(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
            .setSubject(email)
            .claim(TOKEN_TYPE, REFRESH_TOKEN)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtEnv.refreshExp * 1000))
            .compact()
    
}
