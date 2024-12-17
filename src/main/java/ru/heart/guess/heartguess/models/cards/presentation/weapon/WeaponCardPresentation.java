package ru.heart.guess.heartguess.models.cards.presentation.weapon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeaponCardPresentation extends CardPresentation {

    private int attack;

    private int durability;

    private int manaCost;

    private int rarityId;

    private String image;
}
