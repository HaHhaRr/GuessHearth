package ru.heart.guess.heartguess.models.cards.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardPresentation {

    private String name;

    private int attack;

    private int health;

    private int manaCost;

    private String text;

    private int rarityId;

    private int classId;

    private int cardSetId;
}
