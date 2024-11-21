package gsm.gsmkotlin.global.filter

import com.fasterxml.jackson.databind.ObjectMapper
import gsm.gsmkotlin.global.error.ErrorResponse
import gsm.gsmkotlin.global.error.GlobalException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ExceptionHandlerFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        runCatching {
            filterChain.doFilter(request, response)
        }.onFailure { exception ->
            when (exception) {
                is ExpiredJwtException -> exceptionToResponse("만료된 JWT 토큰 입니다.", HttpStatus.UNAUTHORIZED, response)
                is JwtException -> exceptionToResponse("유효하지 않은 JWT 토큰 입니다.", HttpStatus.UNAUTHORIZED, response)
                is GlobalException -> exceptionToResponse(exception.message, exception.httpStatus, response)
            }
        }
    }
    
    private fun exceptionToResponse(message: String, status: HttpStatus, response: HttpServletResponse) {
        response.characterEncoding = "utf-8"
        response.status = status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(
            ErrorResponse(message)
        ))
    }
}
