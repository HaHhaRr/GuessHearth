package ru.heart.guess.heartguess.models.cards.presentation.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCardPresentation extends CardPresentation {

    private int rarityId;

    private int manaCost;

    private String image;
}
