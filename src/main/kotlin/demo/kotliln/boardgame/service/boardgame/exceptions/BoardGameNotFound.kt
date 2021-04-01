package demo.kotliln.boardgame.service.boardgame.exceptions

class BoardGameNotFound(val boardGameIdentifier: String) : RuntimeException(String.format("BoardGame [%s] not found.", boardGameIdentifier))