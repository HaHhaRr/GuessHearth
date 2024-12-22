package ru.heartguess.changer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ru.heartguess.changer.example.NumericChangeableParam;
import ru.heartguess.changer.example.RarityChangeableParam;
import ru.heartguess.changer.example.root.ChangeableParam;
import ru.heartguess.changer.presentation.ChangedNumericParam;
import ru.heartguess.changer.presentation.ChangedRarityParam;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.RarityCardResolver;
import ru.heartguess.models.cards.presentation.ChangedCardPresentation;
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CardChanger {

    @Autowired
    private ChangeableParamsResolver changeableParamsResolver;

    private Random random = new Random();

    public ChangedCardPresentation change(CardPresentation cardPresentation) {
        List<ChangeableParam> changeableParams = changeableParamsResolver.resolveChangeableParams(cardPresentation);
        ChangeableParam changeableParam = changeableParams.get(
                random.nextInt(
                        changeableParams.size()));

        return switch (changeableParam) {
            case NumericChangeableParam numeric -> {
                ChangedNumericParam changedNumericParam = changeNumericParam(numeric);
                yield new ChangedCardPresentation(cardPresentation, changedNumericParam);
            }
            case RarityChangeableParam rarity -> {
                ChangedRarityParam changedRarityParam = changedRarityParam(rarity);
                yield new ChangedCardPresentation(cardPresentation, changedRarityParam);
            }
            default -> throw new IllegalStateException("Unexpected changeableParam value: " + changeableParam);
        };
    }


    private ChangedNumericParam changeNumericParam(NumericChangeableParam numericChangeableParam) {
        List<Integer> options = new ArrayList<>();
        int originalParamValue = numericChangeableParam.getValue();

        while (options.size() != 3) {
            int randomValue = random.nextInt(8) + 1;

            while (options.contains(randomValue) ||
                    randomValue == originalParamValue) {
                randomValue++;
            }
            options.add(randomValue);
        }

        return new ChangedNumericParam(originalParamValue,
                options,
                numericChangeableParam.getChangeableParamType());
    }

    private ChangedRarityParam changedRarityParam(RarityChangeableParam rarityChangeableParam) {
        List<RarityId> options = new ArrayList<>(List.of(RarityId.values()));
        options.removeFirst();
        options.remove(rarityChangeableParam.getRarityId());
        options.remove(random.nextInt(options.size()));
        return new ChangedRarityParam(rarityChangeableParam.getRarityId(),
                options,
                rarityChangeableParam.getChangeableParamType());
    }
}