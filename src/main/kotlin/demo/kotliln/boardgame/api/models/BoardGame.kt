package demo.kotliln.boardgame.api.models

data class BoardGame( val name: String,
                      val category: Category,
                      val rating: Rating,
                      val ageRange: AgeRange,
                      val numberOfPlayers: NumberOfPlayers
)

enum class Category {
    EDUCATIONAL,
    STRATEGY,
    ADVENTURE
}

data class AgeRange(val minimumAge: Int, val maximumAge: Int = 99)

data class NumberOfPlayers(val minimumNumberOfPlayers: Int, val maximumNumberOfPlayers: Int = 4)

data class Rating(val averageRate: Double)