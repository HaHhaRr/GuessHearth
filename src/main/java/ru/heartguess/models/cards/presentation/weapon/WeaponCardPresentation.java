package ru.heartguess.models.cards.presentation.weapon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeaponCardPresentation implements CardPresentation {

    private int attack;

    private int durability;

    private int manaCost;

    private RarityId rarityId;

    private String image;
}
