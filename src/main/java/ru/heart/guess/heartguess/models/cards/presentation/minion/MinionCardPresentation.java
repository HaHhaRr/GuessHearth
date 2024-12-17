package ru.heart.guess.heartguess.models.cards.presentation.minion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinionCardPresentation extends CardPresentation {

    private int attack;

    private int health;

    private int manaCost;

    private int rarityId;

    private String name;

    private String image;
}
