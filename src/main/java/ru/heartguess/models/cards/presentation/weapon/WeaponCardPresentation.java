package ru.heartguess.models.cards.presentation.weapon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heartguess.models.CardType;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeaponCardPresentation implements CardPresentation {

    private final CardType cardType = CardType.WEAPON;

    private int attack;

    private int durability;

    private int manaCost;

    private RarityId rarityId;

    private String name;

    private String image;
}
