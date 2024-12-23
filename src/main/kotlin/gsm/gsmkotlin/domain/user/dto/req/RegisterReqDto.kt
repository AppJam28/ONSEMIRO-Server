package gsm.gsmkotlin.domain.user.dto.req

import gsm.gsmkotlin.domain.user.type.Role

class RegisterReqDto(
    
    val email: String,
    val password: String,
    val name: String,
    val role: Role,
)
