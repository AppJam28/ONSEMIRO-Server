package gsm.gsmkotlin.domain.user

import gsm.gsmkotlin.domain.user.dto.req.ConnectReqDto
import gsm.gsmkotlin.domain.user.dto.req.ContentReqDto
import gsm.gsmkotlin.domain.user.dto.req.LoginServiceReqDto
import gsm.gsmkotlin.domain.user.dto.req.RegisterReqDto
import gsm.gsmkotlin.domain.user.dto.res.ContentResDto
import gsm.gsmkotlin.domain.user.entity.Content
import gsm.gsmkotlin.domain.user.service.ContentService
import gsm.gsmkotlin.domain.user.service.GetContentService
import gsm.gsmkotlin.domain.user.service.LoginService
import gsm.gsmkotlin.domain.user.service.RegisterService
import gsm.gsmkotlin.domain.user.service.RelationService
import gsm.gsmkotlin.global.security.auth.CustomUserDetails
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/relation")
class Controller(
    private val relationService: RelationService,
    private val loginService: LoginService,
    private val registerService: RegisterService,
    private val getContentService: GetContentService,
    private val contentService: ContentService,
    
    
    ) {
    
    @PostMapping("/register")
    fun register(@RequestBody registerReqDto: RegisterReqDto): ResponseEntity<Void> {
        registerService.register(registerReqDto)
        return ResponseEntity.ok().build()
    }
    
    @PostMapping("/login")
    fun logIn(@RequestBody loginServiceReqDto: LoginServiceReqDto,response: HttpServletResponse): ResponseEntity<Void?>? {
        response.addHeader("Authorization",loginService.logIn(loginServiceReqDto).accessToken)
        response.addHeader("Refresh-Token",loginService.logIn(loginServiceReqDto).refreshToken)
        return ResponseEntity.ok().build()
    }
    
    @PostMapping("/connect")
    fun connect(@RequestBody connectReqDto: ConnectReqDto): ResponseEntity<Void> {
        relationService.connectUsers(connectReqDto)
        return ResponseEntity.ok().build()
    }
    
    @PostMapping("/content")
    fun content(@RequestBody contentReqDto: ContentReqDto): ResponseEntity<Void> {
        contentService.returnContent(contentReqDto)
        return ResponseEntity.ok().build()
    }
    
    @GetMapping("/content")
    fun getContent(): ResponseEntity<List<Content>> {
        getContentService.getContent()
        return ResponseEntity.ok().build()
    }
}
