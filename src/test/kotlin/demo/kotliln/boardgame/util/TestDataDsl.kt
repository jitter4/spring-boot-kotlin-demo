package demo.kotliln.boardgame.util

import demo.kotliln.boardgame.api.models.Category
import demo.kotliln.boardgame.service.boardgame.entities.BoardGameEntity
import demo.kotliln.boardgame.service.rating.entities.RateEntity

@DslMarker
annotation class TestDSL

@TestDSL
class BoardGameBuilder(var name: String) {
    private var minimalAge: Int = 10
    private var maximalAge: Int = 99
    private var minNrPlayer: Int = 2
    private var maxNrPlayer: Int = 4

    fun build(): BoardGameEntity {
        return BoardGameEntity(
                id = null,
                name = name,
                category = Category.ADVENTURE,
                minimalAge =  minimalAge,
                maximalAge = maximalAge,
                minimalNumberOfPlayers = minNrPlayer,
                maximalNumberOfPlayers = maxNrPlayer
        )
    }
}

fun A (): BoardGameEntity {
    return game { name = "A" }
}

fun B (): BoardGameEntity {
    return game { name = "B" }
}


fun game(name: String = "", e: BoardGameBuilder.() -> Unit): BoardGameEntity {
    val builder = BoardGameBuilder(name)
    builder.e()
    return builder.build()
}


@TestDSL
class RateEntityBuilder(var assignedTo: String, var rate: Double) {
    fun build() : RateEntity {
        return RateEntity(
                id = null,
                boardGameName = assignedTo,
                rate = rate
        )
    }
}

fun oneOutOfFive(name: String = "", r : RateEntityBuilder.() -> Unit) : RateEntity {
    return rateEntity(1.0, name, r)
}

fun fourOutOfFive(name: String = "", r : RateEntityBuilder.() -> Unit) : RateEntity {
    return rateEntity(4.0, name, r)
}

fun fiveOutOfFive(name: String = "", r : RateEntityBuilder.() -> Unit) : RateEntity {
    return rateEntity(5.0, name, r)
}

private fun rateEntity(d: Double, name: String, r: RateEntityBuilder.() -> Unit): RateEntity {
    val builder = RateEntityBuilder(name, d)
    builder.r()
    return builder.build()
}