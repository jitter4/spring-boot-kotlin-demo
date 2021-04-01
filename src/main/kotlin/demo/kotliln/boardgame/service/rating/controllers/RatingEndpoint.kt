package demo.kotliln.boardgame.service.rating.controllers

import demo.kotliln.boardgame.api.models.BoardGame
import demo.kotliln.boardgame.api.requests.RatingRequest
import demo.kotliln.boardgame.service.boardgame.exceptions.BoardGameNotFound
import demo.kotliln.boardgame.service.rating.BoardGameRatingService
import demo.kotliln.boardgame.service.rating.entities.RateBoardGame
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class RatingEndpoint (private val boardGameRatingService: BoardGameRatingService) {

    @RequestMapping(path = ["/rate"], method = [RequestMethod.POST], consumes = ["application/json"])
    fun rate(@RequestBody ratingRequest: RatingRequest): ResponseEntity<BoardGame> {
        try {
            val rateBoardGame = RateBoardGame(ratingRequest.ratedGame, ratingRequest.rate)
            val ratedBoardGame = boardGameRatingService.ratingBoardGame(rateBoardGame)
            return ResponseEntity.ok().body(ratedBoardGame)
        } catch (boardGameNotFound: BoardGameNotFound) {
            return ResponseEntity.notFound().build()
        } catch (re: RuntimeException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @RequestMapping(path = ["/boardgames"], method = [RequestMethod.GET], consumes = ["application/json"])
    fun boardgamesForMinimalMeanRate(@RequestParam(name = "rate") rate: Double): ResponseEntity<List<BoardGame>> {
        val boardGames = boardGameRatingService.withHigherRateThan(rate)
        return ResponseEntity.ok().body(boardGames)
    }

}