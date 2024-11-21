package gsm.gsmkotlin.global.security.jwt.dto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExp: Int,
    val refreshTokenExp: Int
)
