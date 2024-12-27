package ru.heartguess.changer.service;

import org.junit.jupiter.api.Test;
import ru.heartguess.changer.ChangeableParamType;
import ru.heartguess.changer.model.ChangeableParam;
import ru.heartguess.changer.model.NumericChangeableParam;
import ru.heartguess.changer.model.RarityChangeableParam;
import ru.heartguess.changer.presentation.ChangedNumericParam;
import ru.heartguess.changer.presentation.ChangedRarityParam;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.ChangedCardPresentation;
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;

import static org.junit.jupiter.api.Assertions.*;

class ChangedCardPresentationFactoryTest {

    ChangedCardPresentationFactory changedCardPresentationFactory = new ChangedCardPresentationFactory();

    @Test
    void resolveChangedCardPresentation_NumericChangeableParam() {
        ChangedCardPresentation changedCardPresentation = changedCardPresentationFactory
                .resolveChangedCardPresentation(new MinionCardPresentation(),
                        new NumericChangeableParam(1, ChangeableParamType.MANACOST));

        assertInstanceOf(ChangedNumericParam.class, changedCardPresentation.getChangeableParam());
        MinionCardPresentation minion = (MinionCardPresentation) changedCardPresentation.getCardPresentation();

        assertNotEquals(1, minion.getManaCost());
    }

    @Test
    void resolveChangedCardPresentation_RarityChangeableParam() {
        ChangedCardPresentation changedCardPresentation = changedCardPresentationFactory
                .resolveChangedCardPresentation(new MinionCardPresentation(),
                        new RarityChangeableParam(RarityId.BASE, ChangeableParamType.RARITY));

        assertInstanceOf(ChangedRarityParam.class, changedCardPresentation.getChangeableParam());
        MinionCardPresentation minion = (MinionCardPresentation) changedCardPresentation.getCardPresentation();

        assertNotEquals(RarityId.BASE, minion.getRarityId());
    }

    @Test
    void resolveChangedCardPresentation_InvalidChangeableParam() {
        ChangeableParam changeableParam = new ChangeableParam() {
        };

        assertThrows(IllegalStateException.class, () -> changedCardPresentationFactory
                .resolveChangedCardPresentation(new MinionCardPresentation(),
                        changeableParam));
    }
}