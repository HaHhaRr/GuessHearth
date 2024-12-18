package ru.heart.guess.heartguess.models.cards.presentation.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCardPresentation implements CardPresentation {

    private int rarityId;

    private int manaCost;

    private String image;
}
