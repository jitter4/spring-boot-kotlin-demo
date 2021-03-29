package demo.kotliln.boardgame.service.exceptions;

public class BoardGameNotFound extends RuntimeException {
    public BoardGameNotFound(final String boardGameIdentifier) {
        super(String.format("Boardgame [%s] not found.", boardGameIdentifier));
    }
}
