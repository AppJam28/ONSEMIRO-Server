package gsm.gsmkotlin.domain.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "relation")
class Relation(
    
    @Id
    @Column(name = "relation_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val relation_id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false)
    var user1: User, // 아들
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id", nullable = false)
    var user2: User // 치매
)
