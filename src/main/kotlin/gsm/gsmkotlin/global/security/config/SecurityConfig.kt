package gsm.gsmkotlin.global.security.config

import gsm.gsmkotlin.global.filter.ExceptionHandlerFilter
import gsm.gsmkotlin.global.filter.JwtReqFilter
import gsm.gsmkotlin.global.security.handler.CustomAccessDeniedHandler
import gsm.gsmkotlin.global.security.handler.CustomAuthenticationEntryPointHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*

@Configuration
class SecurityConfig(
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val authenticationEntryPointHandler: CustomAuthenticationEntryPointHandler,
    private val jwtReqFilter: JwtReqFilter,
    private val exceptionHandlerFilter: ExceptionHandlerFilter
) {
    
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        
        http.formLogin { formLogin -> formLogin.disable() }
            .httpBasic { httpBasic -> httpBasic.disable() }
        
        http.csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.configurationSource(corsConfigurationSource()) }
        
        http.exceptionHandling { handling -> handling
            .accessDeniedHandler(accessDeniedHandler)
            .authenticationEntryPoint(authenticationEntryPointHandler)
        }
        
        http.sessionManagement { sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        
        http.addFilterBefore(jwtReqFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(exceptionHandlerFilter, JwtReqFilter::class.java)
        
        http.authorizeHttpRequests { httpRequests -> httpRequests
            .anyRequest().permitAll()
        }
        
        return http.build()
    }
    
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        
        // plz custom allowed client origins
        configuration.allowedOrigins = listOf("*")
        configuration.setAllowedMethods(
            listOf(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
            )
        )
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**",configuration)
        return source
    }
}
