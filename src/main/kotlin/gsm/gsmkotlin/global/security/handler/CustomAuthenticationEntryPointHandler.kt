package gsm.gsmkotlin.global.security.handler

import gsm.gsmkotlin.global.error.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPointHandler : AuthenticationEntryPoint {
    
    private val log = LoggerFactory.getLogger(this::class.simpleName)
    
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        log.info("========== AUTHENTICATION ENTRY POINT ==========")
        
        response.characterEncoding = "utf-8"
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(ObjectMapper().writeValueAsString(
            ErrorResponse("인증에 실패했습니다.")
        ))
    }
}
