package ru.heartguess.changer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.heartguess.changer.model.NumericChangeableParam;
import ru.heartguess.changer.model.RarityChangeableParam;
import ru.heartguess.changer.model.ChangeableParam;
import ru.heartguess.changer.presentation.ChangedNumericParam;
import ru.heartguess.changer.presentation.ChangedRarityParam;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.ChangedCardPresentation;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        int originalParamValue = numericChangeableParam.getValue();
        List<Integer> options = Arrays.stream(
                        IntStream
                                .range(originalParamValue - 1, originalParamValue + 3)
                                .toArray())
                .boxed()
                .toList();

        if (numericChangeableParam
                .getChangeableParamType()
                .equals(ChangeableParamType.HEALTH) && originalParamValue == 1) {
            options = options
                    .stream()
                    .map(value -> value + 1)
                    .toList();
        }

        return new ChangedNumericParam(originalParamValue,
                options,
                numericChangeableParam.getChangeableParamType());
    }

    private ChangedRarityParam changedRarityParam(RarityChangeableParam rarityChangeableParam) {
        List<RarityId> options = new ArrayList<>(List.of(RarityId.values()));
        options.remove(rarityChangeableParam.getRarityId());
        options.remove(random.nextInt(options.size()));
        return new ChangedRarityParam(rarityChangeableParam.getRarityId(),
                options,
                rarityChangeableParam.getChangeableParamType());
    }
}
