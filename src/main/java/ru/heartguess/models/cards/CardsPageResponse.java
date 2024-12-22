package ru.heartguess.models.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.heartguess.models.cards.id.CardId;

import java.util.List;

@Data
public class CardsPageResponse {

    @JsonProperty("cards")
    private List<CardId> cardIds;
}
