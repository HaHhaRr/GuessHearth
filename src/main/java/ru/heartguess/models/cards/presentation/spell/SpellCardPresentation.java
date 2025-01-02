package ru.heartguess.models.cards.presentation.spell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heartguess.models.CardType;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpellCardPresentation implements CardPresentation {

    private final CardType cardType = CardType.SPELL;

    private RarityId rarityId;

    private int manaCost;

    private String name;

    private String image;
}
