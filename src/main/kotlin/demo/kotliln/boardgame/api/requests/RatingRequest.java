package demo.kotliln.boardgame.api.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class RatingRequest {
    @NotBlank
    private final String ratedGame;

    @NotNull
    private final Double rate;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RatingRequest(@JsonProperty("ratedGame") final String ratedGame,
                         @JsonProperty("rate") final Double rate) {
        this.ratedGame = ratedGame;
        this.rate = rate;
    }

}