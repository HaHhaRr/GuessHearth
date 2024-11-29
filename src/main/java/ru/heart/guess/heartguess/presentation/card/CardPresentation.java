package ru.heart.guess.heartguess.presentation.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardPresentation {

    @JsonProperty("attack")
    private int attack;

    @JsonProperty("health")
    private int health;

    @JsonProperty("manaCost")
    private int manaCost;
}
