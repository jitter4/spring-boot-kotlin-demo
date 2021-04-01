package demo.kotliln.boardgame

import demo.kotliln.boardgame.api.models.Rating
import demo.kotliln.boardgame.service.boardgame.entities.BoardGameEntity
import demo.kotliln.boardgame.service.boardgame.repositories.BoardGameRepository
import demo.kotliln.boardgame.service.rating.BoardGameRatingService
import demo.kotliln.boardgame.service.rating.entities.RateBoardGame
import demo.kotliln.boardgame.service.rating.entities.RateEntity
import demo.kotliln.boardgame.service.rating.repositories.RateRepository
import demo.kotliln.boardgame.util.TestData.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.stubbing.OngoingStubbing
import org.mockito.stubbing.Stubber
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class BoardGameRatingServiceTest2 {

    private val boardGameRepository = mock(BoardGameRepository::class.java)

    private val rateRepository = mock(RateRepository::class.java)

    private val boardGameRatingService = BoardGameRatingService(boardGameRepository, rateRepository)

    @Test
    fun `when game is rated then the game with new average rate is returned`() {
        `when`(boardGameRepository.findByName(POPULAR_GAME))
                .returnPopularGame()

        `when`(rateRepository.findByBoardGameName(POPULAR_GAME))
                .returnHighAndLowRate()

        val (_, _, rating, _, _) = boardGameRatingService.ratingBoardGame(RateBoardGame(POPULAR_GAME, lowRate().rate))

        assertEquals(Rating(3.0), rating)
    }

    @Test
    fun `when requesting games with higher rate then 3 only higher games are retrieved`() {
        `when`(boardGameRepository.findAll()).thenReturn(listOf(
                popularBoardGame(),
                notPopularBoardGame()
        ))

        doReturn(listOf(highRate()))
                .whenPopularGameIsRetrieved()
        doReturn(listOf(lowRate()))
                .whenNotPopularGameIsRetrieved()

        val ratedHigherThan3 = boardGameRatingService.withHigherRateThan(3.0)

        assertEquals(1, ratedHigherThan3.size)
        assertNotNull(ratedHigherThan3.find { (name) -> name == POPULAR_GAME })
    }

    fun Stubber.whenPopularGameIsRetrieved() = this.`when`(rateRepository).findByBoardGameName(POPULAR_GAME)
    fun Stubber.whenNotPopularGameIsRetrieved() = this.`when`(rateRepository).findByBoardGameName(NOT_POPULAR_GAME)
    fun OngoingStubbing<Optional<BoardGameEntity>>.returnPopularGame() = this.thenReturn(Optional.of(popularBoardGame()))
    fun OngoingStubbing<List<RateEntity>>.returnHighAndLowRate() = this.thenReturn(listOf(highRate(), lowRate()))
}