package ru.heartguess.changer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.heartguess.changer.model.ChangeableParam;
import ru.heartguess.changer.model.NumericChangeableParam;
import ru.heartguess.changer.model.RarityChangeableParam;
import ru.heartguess.changer.presentation.ChangedNumericParam;
import ru.heartguess.changer.presentation.ChangedRarityParam;
import ru.heartguess.changer.service.ChangeableParamsResolver;
import ru.heartguess.changer.service.RandomChangeableParamSelector;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CardChangerTest {

    @Mock
    private ChangeableParamsResolver changeableParamsResolver;

    @Mock
    private RandomChangeableParamSelector randomChangeableParamSelector;

    @InjectMocks
    private CardChanger cardChanger;

    static MinionCardPresentation cardPresentation;
    static List<ChangeableParam> paramList;

    @BeforeAll
    static void initData() {
        cardPresentation = new MinionCardPresentation();
        paramList = List.of(
                new NumericChangeableParam(cardPresentation.getAttack(), ChangeableParamType.ATTACK),
                new NumericChangeableParam(cardPresentation.getHealth(), ChangeableParamType.HEALTH),
                new NumericChangeableParam(cardPresentation.getManaCost(), ChangeableParamType.MANACOST),
                new RarityChangeableParam(cardPresentation.getRarityId(), ChangeableParamType.RARITY)
        );
    }

    @Test
    @DisplayName("При передачи NumericChangeableParam возвращается объект типа ChangedNumericParam")
    void changeTest_ReturnChangedCardPresentationNumericParam() {

        Mockito.when(changeableParamsResolver.resolveChangeableParams(cardPresentation))
                .thenReturn(paramList);

        Mockito.when(randomChangeableParamSelector.selectParam(paramList))
                .thenReturn(new NumericChangeableParam(1, ChangeableParamType.ATTACK));

        Assertions.assertInstanceOf(ChangedNumericParam.class, cardChanger
                .change(cardPresentation)
                .getChangeableParam());
    }

    @Test
    @DisplayName("При передачи RarityChangeableParam возвращается объект типа ChangedRarityParam")
    void changeTest_ReturnChangedCardPresentationRarityParam() {

        Mockito.when(changeableParamsResolver.resolveChangeableParams(cardPresentation))
                .thenReturn(paramList);

        Mockito.when(randomChangeableParamSelector.selectParam(paramList))
                .thenReturn(new RarityChangeableParam(RarityId.LEGENDARY, ChangeableParamType.ATTACK));

        Assertions.assertInstanceOf(ChangedRarityParam.class, cardChanger
                .change(cardPresentation)
                .getChangeableParam());
    }

    @Test
    @DisplayName("При передачи невалидного ChangeableParam возвращается исключение")
    void changeTest_ReturnChangedCardPresentationInvalidParam() {

        Mockito.when(changeableParamsResolver.resolveChangeableParams(cardPresentation))
                .thenReturn(paramList);

        Mockito.when(randomChangeableParamSelector.selectParam(paramList))
                .thenReturn(new ChangeableParam() {
                });

        Assertions.assertThrows(IllegalStateException.class, () -> cardChanger
                .change(cardPresentation));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    @DisplayName("Для различных значений атаки возвращается валидный список ответов")
    void changeTest_NumericIntValue_ReturnChangedNumericParamValidFields(int paramValue) {

        Mockito.when(changeableParamsResolver.resolveChangeableParams(cardPresentation))
                .thenReturn(paramList);

        Mockito.when(randomChangeableParamSelector.selectParam(paramList))
                .thenReturn(new NumericChangeableParam(paramValue, ChangeableParamType.ATTACK));

        ChangedNumericParam changedNumericParam = (ChangedNumericParam) cardChanger
                .change(cardPresentation)
                .getChangeableParam();

        Assertions.assertEquals(3, changedNumericParam
                .getOptions()
                .stream()
                .filter(value -> value >= 0)
                .toList()
                .size());
        Assertions.assertFalse(changedNumericParam
                .getOptions()
                .contains(paramValue));
        Assertions.assertEquals(ChangeableParamType.ATTACK, changedNumericParam
                .getParamType());
        Assertions.assertEquals(paramValue, changedNumericParam
                .getOriginalValue());
    }

    @ParameterizedTest
    @EnumSource(RarityId.class)
    @DisplayName("Для различных значений редкости возвращается валидный список ответов")
    void changeTest_RarityRarityIdValue_ReturnChangedRarityParamValidFields(RarityId paramValue) {

        Mockito.when(changeableParamsResolver.resolveChangeableParams(cardPresentation))
                .thenReturn(paramList);

        Mockito.when(randomChangeableParamSelector.selectParam(paramList))
                .thenReturn(new RarityChangeableParam(paramValue, ChangeableParamType.RARITY));

        ChangedRarityParam changedRarityParam = (ChangedRarityParam) cardChanger
                .change(cardPresentation)
                .getChangeableParam();

        Assertions.assertEquals(3, changedRarityParam
                .getOptions()
                .size());
        Assertions.assertFalse(changedRarityParam
                .getOptions()
                .contains(paramValue));
        Assertions.assertEquals(ChangeableParamType.RARITY, changedRarityParam
                .getParamType());
        Assertions.assertEquals(paramValue, changedRarityParam
                .getOriginalValue());
    }

    @ParameterizedTest
    @EnumSource(value = ChangeableParamType.class, names = {"ATTACK", "MANACOST"})
    @DisplayName("Для параметров атаки и маны возвращается валидный список ответов")
    void changeTest_ChangeableParamValueAttackAndManacost_OptionsListValuesNotOrEqualsZero(ChangeableParamType paramType) {

        Mockito.when(changeableParamsResolver.resolveChangeableParams(cardPresentation))
                .thenReturn(paramList);

        Mockito.when(randomChangeableParamSelector.selectParam(paramList))
                .thenReturn(new NumericChangeableParam(1, paramType));

        ChangedNumericParam changedNumericParam = (ChangedNumericParam) cardChanger
                .change(cardPresentation)
                .getChangeableParam();

        Assertions.assertEquals(3, changedNumericParam
                .getOptions()
                .stream()
                .filter(value -> value >= 0)
                .toList()
                .size());
    }

    @ParameterizedTest
    @EnumSource(value = ChangeableParamType.class, names = {"HEALTH", "DURABILITY"})
    @DisplayName("Для параметров здоровья и прочности возвращается валидный список ответов")
    void changeTest_ChangeableParamHealthAndDurability_OptionsListValuesNotZero(ChangeableParamType paramType) {

        Mockito.when(changeableParamsResolver.resolveChangeableParams(cardPresentation))
                .thenReturn(paramList);

        Mockito.when(randomChangeableParamSelector.selectParam(paramList))
                .thenReturn(new NumericChangeableParam(1, paramType));

        ChangedNumericParam changedNumericParam = (ChangedNumericParam) cardChanger
                .change(cardPresentation)
                .getChangeableParam();

        Assertions.assertEquals(3, changedNumericParam
                .getOptions()
                .stream()
                .filter(value -> value > 0)
                .toList()
                .size());
    }
}

