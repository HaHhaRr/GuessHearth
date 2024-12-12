package ru.heart.guess.heartguess.models.cards.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangedCard {

    CardPresentation changedCardPresentation;

    private ChangeableParams changeableParam;

    private int originalParamValue;

    public enum ChangeableParams {
        ATTACK, HEALTH, MANACOST
    }
}


