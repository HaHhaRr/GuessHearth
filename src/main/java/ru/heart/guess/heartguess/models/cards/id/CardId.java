package ru.heart.guess.heartguess.models.cards.id;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CardId {

    @JsonProperty("id")
    int cardId;

    @JsonProperty("manaCost")
    int manaCost;
}
