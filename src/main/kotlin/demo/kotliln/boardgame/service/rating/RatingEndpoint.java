package demo.kotliln.boardgame.service.rating;

import demo.kotliln.boardgame.api.models.BoardGame;
import demo.kotliln.boardgame.api.requests.RatingRequest;
import demo.kotliln.boardgame.service.exceptions.BoardGameNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
public class RatingEndpoint {

    private final BoardGameRatingService ratingService;

    @Autowired
    public RatingEndpoint(final BoardGameRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping(
            path = "/rate",
            method = POST,
            consumes = "application/json"
    )
    public ResponseEntity<BoardGame> rate(@RequestBody final RatingRequest ratingRequest) {

        log.info("Rating request coming in for game {} with rate {}",
                ratingRequest.getRatedGame(),
                ratingRequest.getRate()
        );

        try {
            final RateBoardGame rateBoardGame = new RateBoardGame(
                    ratingRequest.getRatedGame(),
                    ratingRequest.getRate()
            );

            final BoardGame ratedBoardGame = ratingService.ratingBoardGame(rateBoardGame);

            return ResponseEntity.ok().body(ratedBoardGame);
        } catch (BoardGameNotFound boardGameNotFound) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @RequestMapping(
            path = "/boardgames",
            method = GET,
            consumes = "application/json"
    )
    public ResponseEntity<List<BoardGame>> boardgamesForMinimalMeanRate(
            @RequestParam(name = "rate") final Double rate
    ) {
        List<BoardGame> boardGames = ratingService.withHigherRateThan(rate);
        return ResponseEntity.ok().body(boardGames);
    }
}