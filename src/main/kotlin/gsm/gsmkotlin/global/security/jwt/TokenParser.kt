package gsm.gsmkotlin.global.security.jwt

import gsm.gsmkotlin.global.security.auth.CustomUserDetailsService
import gsm.gsmkotlin.global.security.jwt.properties.JwtEnvironment
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key

@Component
class TokenParser(
    private val userDetailsService: CustomUserDetailsService,
    private val jwtEnv: JwtEnvironment
) {
    fun authentication(accessToken: String): UsernamePasswordAuthenticationToken {
        val authenticationDetails = userDetailsService.loadUserByUsername(getAccessTokenSubject(accessToken))
        return UsernamePasswordAuthenticationToken(authenticationDetails, "", authenticationDetails.authorities)
    }
    
    private fun getAccessTokenSubject(accessToken: String): String =
        TokenGenerator.getTokenBody(accessToken, Keys.hmacShaKeyFor(jwtEnv.accessSecret.toByteArray(StandardCharsets.UTF_8))).subject
}
