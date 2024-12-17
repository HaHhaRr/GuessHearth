package ru.heart.guess.heartguess.models.cards.presentation.spell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpellCardPresentation extends CardPresentation {

    private int rarityId;

    private int manaCost;

    private String image;
}
