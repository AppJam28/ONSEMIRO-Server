package gsm.gsmkotlin.domain.user.service

import gsm.gsmkotlin.domain.user.dto.req.ConnectReqDto
import gsm.gsmkotlin.domain.user.entity.Relation
import gsm.gsmkotlin.domain.user.entity.User
import gsm.gsmkotlin.domain.user.repository.RelationRepository
import gsm.gsmkotlin.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RelationService(
    private val userRepository: UserRepository,
    private val relationRepository: RelationRepository
) {
    
    @Transactional
    fun connectUsers(connectReqDto: ConnectReqDto) {
        val user1: User = userRepository.findById(connectReqDto.user1Id)
            .orElseThrow { IllegalArgumentException("유저의 번호가 올바르지 않습니다.") }
        
        val user2: User = userRepository.findById(connectReqDto.user2Id)
            .orElseThrow { IllegalArgumentException("유저의 번호가 올바르지 않습니다.") }
        
        val relation = Relation(
            user1 = user1,
            user2 = user2
        )
        
        relationRepository.save(relation)
    }
}
