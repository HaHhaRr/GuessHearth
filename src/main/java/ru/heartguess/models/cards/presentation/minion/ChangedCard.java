package ru.heartguess.models.cards.presentation.minion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangedCard<T> {

    private CardPresentation changedCardPresentation;

    private ChangeableParams changeableParam;

    private T originalParamValue;

    public enum ChangeableParams {
        ATTACK, HEALTH, MANACOST, RARITY, DURABILITY
    }
}


