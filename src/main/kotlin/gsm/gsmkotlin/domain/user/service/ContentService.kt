package gsm.gsmkotlin.domain.user.service

import gsm.gsmkotlin.domain.user.dto.req.ContentReqDto
import gsm.gsmkotlin.domain.user.entity.Content
import gsm.gsmkotlin.domain.user.repository.ContentRepository
import gsm.gsmkotlin.domain.user.repository.RelationRepository
import org.springframework.stereotype.Service

@Service
class ContentService(
    private val contentRepository: ContentRepository,
    private val relationRepository: RelationRepository,
) {
 
    fun returnContent(contentReqDto: ContentReqDto): Unit {
        
        val relation = relationRepository.findById(contentReqDto.relation_id)
            .orElseThrow { IllegalArgumentException("없는 관계 아이디 입니다.") }
        
        val content: Content = Content(
            content = contentReqDto.content,
            relation = relation
        )
        
        contentRepository.save(content)
    }

}
