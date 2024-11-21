package gsm.gsmkotlin.domain.user.entity

import gsm.gsmkotlin.domain.user.type.Authority
import jakarta.persistence.*

@Entity
@Table(name = "tbl_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    val id: Long = 0, // plz custom your table id and create repository

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(100)")
    val email: String,

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    val password: String,

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    val name: String,

    @Column(nullable = false)
    val grade: Int,

    @Column(name = "class_number", nullable = false)
    val classNumber: Int,

    @Column(name = "student_number", nullable = false)
    val studentNumber: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    val authority: Authority
)
