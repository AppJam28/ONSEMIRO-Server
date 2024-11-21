package gsm.gsmkotlin.global.error

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException


@RestControllerAdvice
class GlobalExceptionHandler {
    
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)
    
    @ExceptionHandler(GlobalException::class)
    private fun expectedException(ex: GlobalException): ResponseEntity<ErrorResponse> {
        log.warn("ExpectedException : {} ", ex.message)
        log.trace("ExpectedException Details : ", ex)
        return ResponseEntity.status(ex.httpStatus.value()).body(ErrorResponse.of(ex))
    }
    
    @ExceptionHandler(MethodArgumentNotValidException::class,HttpMessageNotReadableException::class)
    fun validationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        log.warn("Validation Failed : {}", ex.message)
        log.trace("Validation Failed Details : ", ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(ErrorResponse(ex.message))
    }
    
    @ExceptionHandler(RuntimeException::class)
    fun unExpectedException(ex: RuntimeException?): ResponseEntity<ErrorResponse> {
        log.error("UnExpectedException Occur : ", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(ErrorResponse("internal server error has occurred"))
    }
    
    @ExceptionHandler(NoHandlerFoundException::class)
    fun noHandlerFoundException(ex: NoHandlerFoundException): ResponseEntity<ErrorResponse> {
        log.warn("Not Found Endpoint : {}", ex.message)
        log.trace("Not Found Endpoint Details : ", ex)
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(HttpStatus.NOT_FOUND.reasonPhrase))
    }
}
