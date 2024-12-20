package ru.heart.guess.heartguess.models.cards.presentation.spell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpellCardPresentation implements CardPresentation {

    private int rarityId;

    private int manaCost;

    private String image;
}
