package gsm.gsmkotlin.global.error

import org.springframework.http.HttpStatus

class GlobalException(
    override val message: String,
    val httpStatus: HttpStatus
) : RuntimeException(message) {
    constructor(httpStatus: HttpStatus) : this(httpStatus.reasonPhrase, httpStatus)
}
