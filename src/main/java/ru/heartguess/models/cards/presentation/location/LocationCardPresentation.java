package ru.heartguess.models.cards.presentation.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCardPresentation implements CardPresentation {

    private RarityId rarityId;

    private int durability;

    private int manaCost;

    private String name;

    private String image;
}
