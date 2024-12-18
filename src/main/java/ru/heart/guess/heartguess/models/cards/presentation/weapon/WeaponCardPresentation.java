package ru.heart.guess.heartguess.models.cards.presentation.weapon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeaponCardPresentation implements CardPresentation {

    private int attack;

    private int durability;

    private int manaCost;

    private int rarityId;

    private String image;
}
