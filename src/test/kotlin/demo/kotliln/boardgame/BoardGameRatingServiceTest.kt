package demo.kotliln.boardgame

import demo.kotliln.boardgame.api.models.Rating
import demo.kotliln.boardgame.service.boardgame.repositories.BoardGameRepository
import demo.kotliln.boardgame.service.rating.BoardGameRatingService
import demo.kotliln.boardgame.service.rating.entities.RateBoardGame
import demo.kotliln.boardgame.service.rating.repositories.RateRepository
import demo.kotliln.boardgame.util.TestData.*
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BoardGameRatingService2Test {

    private val boardGameRepository = mockk<BoardGameRepository>()

    private val rateRepository = mockk<RateRepository>()

    private val boardGameRatingService = BoardGameRatingService(boardGameRepository, rateRepository)

    @Test
    fun `when game is rated then the game with new average rate is returned`() {
        every {
            boardGameRepository.findByName(POPULAR_GAME)
        } returns Optional.of(popularBoardGame())

        every {
            rateRepository.save(any())
        } returns highRate()

        every {
            rateRepository.findByBoardGameName(POPULAR_GAME)
        } returns listOf(highRate(), lowRate())

        val (_, _, rating, _, _) = boardGameRatingService.ratingBoardGame(RateBoardGame(POPULAR_GAME, lowRate().rate))

        assertEquals(Rating(3.0), rating)
    }

    @Test
    fun `when requesting games with higher rate then 3 only higher games are retrieved`() {
        every {
            boardGameRepository.findAll()

        } returns listOf(popularBoardGame(), notPopularBoardGame())

        every {
            rateRepository.findByBoardGameName(POPULAR_GAME)
        } returns listOf(highRate())

        every {
            rateRepository.findByBoardGameName(NOT_POPULAR_GAME)
        } returns listOf(lowRate())

        val ratedHigherThan3 = boardGameRatingService.withHigherRateThan(3.0)

        assertEquals(1, ratedHigherThan3.size)
        assertNotNull(ratedHigherThan3.find { (name) -> name == POPULAR_GAME })
    }
}