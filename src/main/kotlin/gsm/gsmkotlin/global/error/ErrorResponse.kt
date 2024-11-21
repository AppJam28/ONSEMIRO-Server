package gsm.gsmkotlin.global.error

class ErrorResponse(
    val message: String
) {
    companion object {
        fun of(cause: Throwable): ErrorResponse {
            return ErrorResponse(cause.message ?: "An unknown error occurred")
        }
    }
}
