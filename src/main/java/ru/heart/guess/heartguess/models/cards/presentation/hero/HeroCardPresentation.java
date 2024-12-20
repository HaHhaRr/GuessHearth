package ru.heart.guess.heartguess.models.cards.presentation.hero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroCardPresentation implements CardPresentation {

    private int rarityId;

    private int manaCost;

    private String image;
}
