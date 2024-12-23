package gsm.gsmkotlin.domain.user.entity

import gsm.gsmkotlin.domain.user.type.Role
import jakarta.persistence.*

@Entity
@Table(name = "tbl_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    val id: Long = 0,
    
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(100)")
    val email: String,
    
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    val password: String,
    
    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    val name: String,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role
)
