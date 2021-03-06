package demo.kotliln.boardgame

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import demo.kotliln.boardgame.api.models.*
import demo.kotliln.boardgame.api.requests.RatingRequest
import demo.kotliln.boardgame.service.boardgame.entities.BoardGameEntity
import demo.kotliln.boardgame.service.boardgame.repositories.BoardGameRepository
import demo.kotliln.boardgame.service.rating.entities.RateEntity
import demo.kotliln.boardgame.service.rating.repositories.RateRepository
import demo.kotliln.boardgame.util.TestData.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext
import java.util.*
import kotlin.jvm.Throws


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class RatingEndpointITest {

    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var  webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var  boardGameRepository: BoardGameRepository

    @Autowired
    private lateinit var  rateRepository: RateRepository

    private val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        mvc = webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun `Rating an existing boardGame will update the grade`() {

        // insert a board game
        boardGameRepository.save(popularBoardGame())

        // insert a rate for this game
        rateRepository.save(RateEntity(null, POPULAR_GAME, 5.0))

        // rate this game again
        val dominion = RatingRequest(POPULAR_GAME, 5.0)
        val content = objectMapper.writeValueAsString(dominion)

        // post the rate and verify the response
        mvc.perform(post("/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
                .andExpect(status().isOk)
    }


    @Test
    fun `Possible to retrieve a game with some higher rate`() {

        // save a popular board game with high rates (on scale of 5)
        boardGameRepository.save(popularBoardGame())
        rateRepository.save(RateEntity(null, POPULAR_GAME, 5.0))
        rateRepository.save(RateEntity(null, POPULAR_GAME, 4.0))

        // save a not so popular board game with low rates (on scale of 5)
        boardGameRepository.save(notPopularBoardGame())
        rateRepository.save(RateEntity(null, NOT_POPULAR_GAME, 1.0))
        rateRepository.save(RateEntity(null, NOT_POPULAR_GAME, 1.0))

        // retrieve all the games with a mean rate above 2 and verify the response
        mvc.perform(
                get("/boardgames?rate=2")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk).andExpect(content().json(expectedGame(popularBoardGame(), 4.5)))
    }


    @AfterEach
    fun cleanUp() {
        rateRepository.deleteAll()
        boardGameRepository.deleteAll()
    }

    @Throws(JsonProcessingException::class)
    private fun expectedGame(en: BoardGameEntity, expectedRate: Double?): String {
        val game = BoardGame(en.name,
                Category.valueOf(en.category.name),
                Rating(expectedRate!!),
                AgeRange(en.minimalAge, en.maximalAge),
                NumberOfPlayers(en.minimalNumberOfPlayers, en.maximalNumberOfPlayers),
        )

        return objectMapper.writeValueAsString(Arrays.asList(game))

    }
}