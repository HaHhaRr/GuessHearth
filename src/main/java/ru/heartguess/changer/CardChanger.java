package ru.heartguess.changer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.heartguess.changer.model.ChangeableParam;
import ru.heartguess.changer.model.NumericChangeableParam;
import ru.heartguess.changer.model.RarityChangeableParam;
import ru.heartguess.changer.presentation.ChangedNumericParam;
import ru.heartguess.changer.presentation.ChangedRarityParam;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.ChangedCardPresentation;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
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
                ChangedRarityParam changedRarityParam = changeRarityParam(rarity);
                yield new ChangedCardPresentation(cardPresentation, changedRarityParam);
            }
            default -> throw new IllegalStateException("Unexpected changeableParam value: " + changeableParam);
        };
    }

    private ChangedNumericParam changeNumericParam(NumericChangeableParam numericChangeableParam) {
        int originalParamValue = numericChangeableParam.getValue();
        int startInclusive = originalParamValue - 3;
        int endExclusive = originalParamValue + 4;
        int optionsLength = endExclusive - startInclusive;
        int fromIndex = random.nextInt(optionsLength - 3);

        boolean isHealthParam = numericChangeableParam
                .getChangeableParamType()
                .equals(ChangeableParamType.HEALTH);
        boolean isDurabilityParam = numericChangeableParam
                .getChangeableParamType()
                .equals(ChangeableParamType.DURABILITY);

        List<Integer> options = Arrays.stream(
                        IntStream
                                .range(startInclusive, endExclusive)
                                .toArray())
                .boxed()
                .toList()
                .subList(fromIndex, fromIndex + 4)
                .stream()
                .filter(value -> value != originalParamValue)
                .toList();

        if (options.getFirst() <= 0) {
            int firstValue = -options.getFirst();
            int sumValue = isHealthParam || isDurabilityParam ?
                    firstValue + 1 :
                    firstValue;
            options = options
                    .stream()
                    .map(value -> value + sumValue)
                    .toList();
        }

        return new ChangedNumericParam(originalParamValue,
                options,
                numericChangeableParam.getChangeableParamType());
    }

    private ChangedRarityParam changeRarityParam(RarityChangeableParam rarityChangeableParam) {
        List<RarityId> options = new ArrayList<>(List.of(RarityId.values()));
        options.remove(rarityChangeableParam.getRarityId());
        options.remove(random.nextInt(options.size()));
        return new ChangedRarityParam(rarityChangeableParam.getRarityId(),
                options,
                rarityChangeableParam.getChangeableParamType());
    }
}
