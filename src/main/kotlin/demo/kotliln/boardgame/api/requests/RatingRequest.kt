package demo.kotliln.boardgame.api.requests

import javax.validation.constraints.NotBlank

data class RatingRequest(@NotBlank val ratedGame: String, val rate: Double)