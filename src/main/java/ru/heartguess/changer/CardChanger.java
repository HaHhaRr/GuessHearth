package ru.heartguess.changer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.heartguess.changer.model.ChangeableParam;
import ru.heartguess.changer.service.ChangeableParamsResolver;
import ru.heartguess.changer.service.ChangedCardPresentationFactory;
import ru.heartguess.changer.service.RandomChangeableParamSelector;
import ru.heartguess.models.cards.presentation.ChangedCardPresentation;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

import java.util.List;

@Slf4j
@Service
public class CardChanger {

    @Autowired
    private ChangeableParamsResolver changeableParamsResolver;

    @Autowired
    private RandomChangeableParamSelector randomChangeableParamSelector;

    @Autowired
    private ChangedCardPresentationFactory changedCardPresentationFactory;

    public ChangedCardPresentation change(CardPresentation cardPresentation) {
        List<ChangeableParam> changeableParams = changeableParamsResolver.resolveChangeableParams(cardPresentation);
        ChangeableParam changeableParam = randomChangeableParamSelector.selectParam(changeableParams);
        return changedCardPresentationFactory.resolveChangedCardPresentation(cardPresentation, changeableParam);
    }
}
