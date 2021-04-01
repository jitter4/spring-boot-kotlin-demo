package demo.kotliln.boardgame.service.rating

import demo.kotliln.boardgame.api.models.*
import demo.kotliln.boardgame.service.boardgame.entities.BoardGameEntity
import demo.kotliln.boardgame.service.boardgame.exceptions.BoardGameNotFound
import demo.kotliln.boardgame.service.boardgame.repositories.BoardGameRepository
import demo.kotliln.boardgame.service.rating.entities.RateBoardGame
import demo.kotliln.boardgame.service.rating.entities.RateEntity
import demo.kotliln.boardgame.service.rating.repositories.RateRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
open class BoardGameRatingService (
        private val boardGameRepository: BoardGameRepository,
        private val rateRepository: RateRepository
) {
    private val logger = KotlinLogging.logger { }

    fun ratingBoardGame(rateBoardGame: RateBoardGame): BoardGame {
        val rateEntity = RateEntity(null, rateBoardGame.boardGameName, rateBoardGame.rate)

        logger.debug { "Saving new rating entity for boardgame ${rateBoardGame.boardGameName}" }
        rateRepository.save(rateEntity)
        logger.debug { "Return updated rate for boardgame ${rateBoardGame.boardGameName}" }

        return boardGameRepository.findByName(rateBoardGame.boardGameName)
                .map { this.ratedBoardGame(it) }
                .orElseThrow { BoardGameNotFound(rateBoardGame.boardGameName) }
    }

    fun withHigherRateThan(rate: Double): List<BoardGame> {
        logger.debug("Find all boardgames with rate higher than $rate")

        return boardGameRepository.findAll()
                .map{ this.ratedBoardGame(it) }
                .filter { it.rating.averageRate > rate }
    }

    private fun ratedBoardGame(bg: BoardGameEntity): BoardGame {
        logger.debug("Retrieving all the rates of boardgame ${bg.name}")

        val ratesOfTheBoardGame = rateRepository.findByBoardGameName(bg.name)
        val average = ratesOfTheBoardGame.asSequence().map { it.rate }.average()

        return BoardGame(
                bg.name,
                Category.valueOf(bg.category.name),
                Rating(average),
                AgeRange(bg.minimalAge, bg.maximalAge),
                NumberOfPlayers(bg.minimalNumberOfPlayers, bg.maximalNumberOfPlayers),
        )
    }


}