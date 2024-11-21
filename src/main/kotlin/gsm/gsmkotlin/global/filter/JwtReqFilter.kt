package gsm.gsmkotlin.global.filter

import gsm.gsmkotlin.global.security.jwt.TokenParser
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtReqFilter(
    private val tokenParser: TokenParser
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        var accessToken = request.getHeader(AUTHORIZATION_HEADER)
        
        if (accessToken != null && accessToken.startsWith(BEARER_PREFIX)) {
            accessToken = accessToken.replace(BEARER_PREFIX, "")
            
            val authentication = tokenParser.authentication(accessToken)
            SecurityContextHolder.clearContext()
            SecurityContextHolder.getContext().authentication = authentication
        }
        
        filterChain.doFilter(request, response)
    }
    
    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "
    }
}
