package demo.kotliln.boardgame.service.boardgame.entities

import demo.kotliln.boardgame.api.models.Category
import javax.persistence.*

@Entity
data class BoardGameEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "name", nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    val category: Category,

    @Column(name = "minNumberOfPlayers", nullable = false)
    val minimalNumberOfPlayers: Int,

    @Column(name = "maxNumberOfPlayers", nullable = false)
    val maximalNumberOfPlayers: Int,

    @Column(name = "minimalAge", nullable = false)
    val minimalAge: Int,

    @Column(name = "maximalAge", nullable = false)
    val maximalAge: Int

)