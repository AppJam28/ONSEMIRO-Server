package gsm.gsmkotlin.global.security.jwt.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtEnvironment(
    val accessSecret: String,
    val refreshSecret: String,
    val accessExp: Int,
    val refreshExp: Int
)
