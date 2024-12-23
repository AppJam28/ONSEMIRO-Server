package gsm.gsmkotlin.domain.user.service

import gsm.gsmkotlin.domain.user.dto.res.ContentResDto
import gsm.gsmkotlin.domain.user.entity.Content
import gsm.gsmkotlin.domain.user.entity.User
import gsm.gsmkotlin.domain.user.repository.ContentRepository
import gsm.gsmkotlin.domain.user.repository.RelationRepository
import gsm.gsmkotlin.domain.user.repository.UserRepository
import gsm.gsmkotlin.global.util.UserUtil
import org.springframework.stereotype.Service

@Service
class GetContentService(
    private val contentRepository: ContentRepository,
    private val userUtil: UserUtil
) {
    
    fun getContent(): List<Content> {
        
        
        val content = contentRepository.findByUserId(userUtil.getCurrentUser().id)
        
        return content
    }
}
