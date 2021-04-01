package demo.kotliln.boardgame.service.rating.repositories

import demo.kotliln.boardgame.service.rating.entities.RateEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RateRepository : CrudRepository<RateEntity, Long> {
    fun findByBoardGameName(boardGameName: String): List<RateEntity>
}