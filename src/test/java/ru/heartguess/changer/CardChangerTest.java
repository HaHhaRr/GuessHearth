package ru.heartguess.changer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.heartguess.changer.model.ChangeableParam;
import ru.heartguess.changer.model.NumericChangeableParam;
import ru.heartguess.changer.model.RarityChangeableParam;
import ru.heartguess.changer.presentation.ChangedNumericParam;
import ru.heartguess.changer.presentation.ChangedRarityParam;
import ru.heartguess.changer.presentation.root.ChangedParam;
import ru.heartguess.changer.service.ChangeableParamsResolver;
import ru.heartguess.changer.service.RandomChangeableParamSelector;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;

@ExtendWith(MockitoExtension.class)
class CardChangerTest {

    @Mock
    private RandomChangeableParamSelector randomChangeableParamSelector;

    private CardChanger cardChanger;
    MinionCardPresentation cardPresentation;

    @BeforeEach
    void initData() {
        cardPresentation = new MinionCardPresentation();
        cardChanger = new CardChanger(new ChangeableParamsResolver(), randomChangeableParamSelector);
    }

    @Test
    @DisplayName("При передачи NumericChangeableParam возвращается объект типа ChangedNumericParam")
    void changeTest_ReturnChangedCardPresentationNumericParam() {
        configureChangeableParamSelector(new NumericChangeableParam(1, ChangeableParamType.ATTACK));

        ChangedParam changedParam = cardChanger.change(cardPresentation).getChangeableParam();

        Assertions.assertInstanceOf(ChangedNumericParam.class, changedParam);
    }

    @Test
    @DisplayName("При передачи RarityChangeableParam возвращается объект типа ChangedRarityParam")
    void changeTest_ReturnChangedCardPresentationRarityParam() {
        configureChangeableParamSelector(new RarityChangeableParam(RarityId.LEGENDARY, ChangeableParamType.ATTACK));

        ChangedParam changedParam = cardChanger.change(cardPresentation).getChangeableParam();

        Assertions.assertInstanceOf(ChangedRarityParam.class, changedParam);
    }

    @Test
    @DisplayName("При передачи невалидного ChangeableParam кидается исключение")
    void changeTest_ReturnChangedCardPresentationInvalidParam() {
        configureChangeableParamSelector(new ChangeableParam() {
        });

        Assertions.assertThrows(IllegalStateException.class, () -> cardChanger
                .change(cardPresentation));
    }

    @SuppressWarnings("checkstyle:magicnumber")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    @DisplayName("Для различных значений атаки возвращается валидный список ответов")
    void changeTest_NumericIntValue_ReturnChangedNumericParamValidFields(int paramValue) {
        configureChangeableParamSelector(new NumericChangeableParam(paramValue, ChangeableParamType.ATTACK));

        ChangedNumericParam changedNumericParam = (ChangedNumericParam) cardChanger
                .change(cardPresentation)
                .getChangeableParam();

        Assertions.assertEquals(3, changedNumericParam
                .getOptions()
                .size());
        Assertions.assertTrue(changedNumericParam
                .getOptions()
                .stream()
                .allMatch(value -> value >= 0));
        Assertions.assertFalse(changedNumericParam
                .getOptions()
                .contains(paramValue));
        Assertions.assertEquals(ChangeableParamType.ATTACK, changedNumericParam
                .getParamType());
        Assertions.assertEquals(paramValue, changedNumericParam
                .getOriginalValue());
    }

    @SuppressWarnings("checkstyle:magicnumber")
    @ParameterizedTest
    @EnumSource(RarityId.class)
    @DisplayName("Для различных значений редкости возвращается валидный список ответов")
    void changeTest_RarityRarityIdValue_ReturnChangedRarityParamValidFields(RarityId paramValue) {

        configureChangeableParamSelector(new RarityChangeableParam(paramValue, ChangeableParamType.RARITY));

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

    @SuppressWarnings("checkstyle:magicnumber")
    @ParameterizedTest
    @EnumSource(value = ChangeableParamType.class, names = {"ATTACK", "MANACOST"})
    @DisplayName("Для параметров атаки и маны возвращается валидный список ответов")
    void changeTest_ChangeableParamValueAttackAndManacost_OptionsListValuesNotOrEqualsZero(
            ChangeableParamType paramType) {

        configureChangeableParamSelector(new NumericChangeableParam(1, paramType));

        ChangedNumericParam changedNumericParam = (ChangedNumericParam) cardChanger
                .change(cardPresentation)
                .getChangeableParam();

        Assertions.assertEquals(3, changedNumericParam
                .getOptions()
                .size());
        Assertions.assertTrue(changedNumericParam
                .getOptions()
                .stream()
                .allMatch(value -> value >= 0));
    }

    @SuppressWarnings("checkstyle:magicnumber")
    @ParameterizedTest
    @EnumSource(value = ChangeableParamType.class, names = {"HEALTH", "DURABILITY"})
    @DisplayName("Для параметров здоровья и прочности возвращается валидный список ответов")
    void changeTest_ChangeableParamHealthAndDurability_OptionsListValuesNotZero(ChangeableParamType paramType) {

        Mockito.when(randomChangeableParamSelector.selectParam(Mockito.any()))
                .thenReturn(new NumericChangeableParam(1, paramType));

        ChangedNumericParam changedNumericParam = (ChangedNumericParam) cardChanger
                .change(cardPresentation)
                .getChangeableParam();

        Assertions.assertEquals(3, changedNumericParam
                .getOptions()
                .size());
        Assertions.assertTrue(changedNumericParam
                .getOptions()
                .stream()
                .allMatch(value -> value > 0));
    }

    private void configureChangeableParamSelector(ChangeableParam changeableParam) {
        Mockito.when(randomChangeableParamSelector.selectParam(Mockito.any())).thenReturn(changeableParam);
    }
}

