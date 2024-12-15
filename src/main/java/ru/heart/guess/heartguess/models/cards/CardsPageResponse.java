package ru.heart.guess.heartguess.models.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.heart.guess.heartguess.models.cards.id.CardId;

import java.util.List;

@Data
public class CardsPageResponse {

    @JsonProperty("cards")
    private List<CardId> cardIds;
}
