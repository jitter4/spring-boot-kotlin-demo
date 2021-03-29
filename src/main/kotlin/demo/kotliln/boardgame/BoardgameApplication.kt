package demo.kotliln.boardgame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoardgameApplication

fun main(args: Array<String>) {
	runApplication<BoardgameApplication>(*args)
}