package ru.heartguess.models.cards.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.heartguess.changer.presentation.root.ChangedParam;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

@Data
@AllArgsConstructor
public class ChangedCardPresentation {

    private CardPresentation cardPresentation;

    private ChangedParam changeableParam;
}
