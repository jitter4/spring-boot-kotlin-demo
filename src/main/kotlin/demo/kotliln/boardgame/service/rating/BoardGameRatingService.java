package demo.kotliln.boardgame.service.rating;

import demo.kotliln.boardgame.api.models.AgeRange;
import demo.kotliln.boardgame.api.models.BoardGame;
import demo.kotliln.boardgame.api.models.Category;
import demo.kotliln.boardgame.api.models.NumberOfPlayers;
import demo.kotliln.boardgame.api.models.Rating;
import demo.kotliln.boardgame.service.boardgame.BoardGameRepository;
import demo.kotliln.boardgame.service.entities.BoardGameEntity;
import demo.kotliln.boardgame.service.exceptions.BoardGameNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardGameRatingService {
    private final BoardGameRepository boardGameRepository;
    private final RateRepository rateRepository;

    @Autowired
    public BoardGameRatingService(final BoardGameRepository boardGameRepository, final RateRepository rateRepository) {
        this.boardGameRepository = boardGameRepository;
        this.rateRepository = rateRepository;
    }

    public BoardGame ratingBoardGame(final RateBoardGame rateBoardGame) throws BoardGameNotFound {
        final RateEntity rate = new RateEntity(null,
                rateBoardGame.getBoardGameName(),
                rateBoardGame.getRate()
        );

        log.debug("Saving new rating entity for boardgame {}",
                rateBoardGame.getBoardGameName()
        );

        rateRepository.save(rate);

        log.debug("Return updated rate for boardgame {}",
                rateBoardGame.getBoardGameName()
        );

        return boardGameRepository.findByName(rateBoardGame.getBoardGameName())
                .map(this::ratedBoardGame)
                .orElseThrow(
                        () -> new BoardGameNotFound(rateBoardGame.getBoardGameName())
                );
    }

    public List<BoardGame> withHigherRateThan(final Double rate) {
        log.debug("Find all boardgames with rate higher than {}", rate);

        return boardGameRepository.findAll()
                .stream()
                .map(this::ratedBoardGame)
                .filter(game -> game.getRating().getAverageRate() > rate)
                .collect(Collectors.toList());
    }

    private BoardGame ratedBoardGame(final BoardGameEntity bg) {
        log.debug("Retrieving all the rates of boardgame {}", bg.getName());

        final List<RateEntity> ratesOfTheBoardGame = rateRepository.findByBoardGameName(bg.getName());
        final Double average = ratesOfTheBoardGame.stream()
                .mapToDouble(RateEntity::getRate)
                .average()
                .getAsDouble();

        return new BoardGame(bg.getName(), Category.valueOf(bg.getCategory().name()),
                new Rating(average),
                new AgeRange(bg.getMinimalAge(), bg.getMaximalAge()),
                new NumberOfPlayers(bg.getMinimalNumberOfPlayers(), bg.getMaximalNumberOfPlayers())
        );
    }
}