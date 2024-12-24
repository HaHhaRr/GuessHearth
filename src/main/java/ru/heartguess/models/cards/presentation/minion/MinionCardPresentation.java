package ru.heartguess.models.cards.presentation.minion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinionCardPresentation implements CardPresentation {

    private int attack;

    private int health;

    private int manaCost;

    private RarityId rarityId;

    private String name;

    private String image;
}
