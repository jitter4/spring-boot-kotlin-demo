package demo.kotliln.boardgame.util;

import demo.kotliln.boardgame.api.models.Category;
import demo.kotliln.boardgame.service.boardgame.entities.BoardGameEntity;
import demo.kotliln.boardgame.service.rating.entities.RateEntity;

public class TestData {

    public static BoardGameEntity popularBoardGame() {
        return getBoardGameEntity(POPULAR_GAME);
    }

    public static BoardGameEntity notPopularBoardGame() {
        return getBoardGameEntity(NOT_POPULAR_GAME);
    }

    private static BoardGameEntity getBoardGameEntity(String inpopularGame) {
        return new BoardGameEntity(
                null,
                inpopularGame, Category.ADVENTURE,
                MINIMAL_AGE, MAXIMAL_AGE,
                MIN_NR_PLAYERS, MAX_NR_PLAYERS
        );
    }

    public static RateEntity highRate() {
        return new RateEntity(1L, POPULAR_GAME, 5.0d);
    }

    public static RateEntity lowRate() {
        return new RateEntity(2L, NOT_POPULAR_GAME, 1.0d);
    }


    public static final String POPULAR_GAME = "A";
    public static final String NOT_POPULAR_GAME = "B";
    public static final Integer MINIMAL_AGE = 10;
    public static final Integer MAXIMAL_AGE = 99;
    public static final Integer MIN_NR_PLAYERS = 2;
    public static final Integer MAX_NR_PLAYERS = 4;

}