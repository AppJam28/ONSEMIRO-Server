package gsm.gsmkotlin.domain.user.repository

import gsm.gsmkotlin.domain.user.entity.Relation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RelationRepository : JpaRepository<Relation, Long> {
}
