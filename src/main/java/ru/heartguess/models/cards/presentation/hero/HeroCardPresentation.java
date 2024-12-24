package ru.heartguess.models.cards.presentation.hero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroCardPresentation implements CardPresentation {

    private RarityId rarityId;

    private int manaCost;

    private String name;

    private String image;
}
