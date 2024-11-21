package gsm.gsmkotlin.global.config.properties

import gsm.gsmkotlin.global.security.jwt.properties.JwtEnvironment
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(
    basePackageClasses = [
        JwtEnvironment::class
    ]
)
class PropertiesScanConfig
