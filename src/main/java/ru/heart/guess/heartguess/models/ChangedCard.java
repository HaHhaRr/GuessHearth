package ru.heart.guess.heartguess.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.heart.guess.heartguess.presentation.card.CardPresentation;

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


