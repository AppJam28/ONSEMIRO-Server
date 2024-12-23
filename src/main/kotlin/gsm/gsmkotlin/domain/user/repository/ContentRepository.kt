package gsm.gsmkotlin.domain.user.repository

import gsm.gsmkotlin.domain.user.entity.Content
import gsm.gsmkotlin.domain.user.entity.Relation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ContentRepository : JpaRepository<Content, Long> {
    @Query("SELECT c FROM Content c WHERE c.relation IN (SELECT r FROM Relation r WHERE r.user1.id = :userId OR r.user2.id = :userId)")
    fun findByUserId(userId: Long): List<Content>
}
