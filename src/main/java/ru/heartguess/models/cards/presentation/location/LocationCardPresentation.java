package ru.heartguess.models.cards.presentation.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heartguess.models.CardType;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCardPresentation implements CardPresentation {

    private final CardType cardType = CardType.LOCATION;

    private RarityId rarityId;

    private int durability;

    private int manaCost;

    private String name;

    private String image;
}
