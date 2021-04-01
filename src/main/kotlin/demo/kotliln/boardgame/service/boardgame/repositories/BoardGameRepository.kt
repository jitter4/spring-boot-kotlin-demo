package demo.kotliln.boardgame.service.boardgame.repositories

import demo.kotliln.boardgame.service.boardgame.entities.BoardGameEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BoardGameRepository : CrudRepository<BoardGameEntity, Long> {
    fun findByName(name: String): Optional<BoardGameEntity>

    override fun findAll(): List<BoardGameEntity>
}