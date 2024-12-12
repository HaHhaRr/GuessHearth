package ru.heart.guess.heartguess.models.cards.id;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class IdsList {

    @JsonProperty("pageCount")
    public int pageCount;

    @JsonProperty("cards")
    public List<CardId> cardIds;
}
