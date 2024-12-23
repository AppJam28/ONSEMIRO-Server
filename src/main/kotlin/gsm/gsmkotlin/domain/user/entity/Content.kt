package gsm.gsmkotlin.domain.user.entity

import jakarta.persistence.*

@Entity
class Content(
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false, unique = true)
    var content: String,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_id", nullable = false)
    val relation: Relation
)
