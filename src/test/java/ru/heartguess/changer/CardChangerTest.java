package ru.heartguess.changer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.heartguess.changer.model.NumericChangeableParam;
import ru.heartguess.changer.model.RarityChangeableParam;
import ru.heartguess.changer.presentation.ChangedNumericParam;
import ru.heartguess.changer.presentation.ChangedRarityParam;
import ru.heartguess.models.RarityId;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class CardChangerTest {

    @Mock
    private ChangeableParamsResolver changeableParamsResolver;

    @InjectMocks
    private CardChanger cardChanger;

    private static Method changeNumericParamMethod;
    private static Method changeRarityParamMethod;

    @BeforeAll
    static void initPrivateMethods() throws NoSuchMethodException {
        changeNumericParamMethod = CardChanger.class
                .getDeclaredMethod("changeNumericParam", NumericChangeableParam.class);
        changeNumericParamMethod.setAccessible(true);

        changeRarityParamMethod = CardChanger.class
                .getDeclaredMethod("changeRarityParam", RarityChangeableParam.class);
        changeRarityParamMethod.setAccessible(true);
    }

    @ParameterizedTest(name = "{index} - originalParamValue equals {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void changeNumericParamTest_ParamTypeAttack_ReturnValuesMoreOrEqualsZero(int originalParamValue)
            throws InvocationTargetException, IllegalAccessException {
        ChangedNumericParam changedNumericParam = (ChangedNumericParam) changeNumericParamMethod.invoke(
                cardChanger, new NumericChangeableParam(originalParamValue, ChangeableParamType.ATTACK));

        assertEquals(3, changedNumericParam
                .getOptions()
                .stream()
                .filter(value -> value >= 0)
                .toList()
                .size());
    }

    @ParameterizedTest(name = "{index} - originalParamValue equals {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void changeNumericParamTest_ParamTypeManacost_ReturnValuesMoreOrEqualsZero(int originalParamValue)
            throws InvocationTargetException, IllegalAccessException {
        ChangedNumericParam changedNumericParam = (ChangedNumericParam) changeNumericParamMethod.invoke(
                cardChanger, new NumericChangeableParam(originalParamValue, ChangeableParamType.MANACOST));

        assertEquals(3, changedNumericParam
                .getOptions()
                .stream()
                .filter(value -> value >= 0)
                .toList()
                .size());
    }

    @ParameterizedTest(name = "{index} - originalParamValue equals {0}")
    @ValueSource(ints = {1, 2, 3, 4})
    void changeNumericParamTest_ParamTypeHealth_ReturnValuesMoreZero(int originalParamValue)
            throws InvocationTargetException, IllegalAccessException {
        ChangedNumericParam changedNumericParam = (ChangedNumericParam) changeNumericParamMethod.invoke(
                cardChanger, new NumericChangeableParam(originalParamValue, ChangeableParamType.HEALTH));

        assertEquals(3, changedNumericParam
                .getOptions()
                .stream()
                .filter(value -> value > 0)
                .toList()
                .size());
    }

    @ParameterizedTest(name = "{index} - originalParamValue equals {0}")
    @ValueSource(ints = {1, 2, 3, 4})
    void changeNumericParamTest_ParamTypeDurability_ReturnValuesMoreZero(int originalParamValue)
            throws InvocationTargetException, IllegalAccessException {
        ChangedNumericParam changedNumericParam = (ChangedNumericParam) changeNumericParamMethod.invoke(
                cardChanger, new NumericChangeableParam(originalParamValue, ChangeableParamType.DURABILITY));

        assertEquals(3, changedNumericParam
                .getOptions()
                .stream()
                .filter(value -> value > 0)
                .toList()
                .size());
    }

    @ParameterizedTest(name = "{index} - originalParamValue equals {0}")
    @EnumSource(value = RarityId.class, names = {"COMMON", "BASE", "RARE", "EPIC", "LEGENDARY"})
    void changeRarityParamTest_ParamTypeRarity_ReturnValuesWithoutOriginalValue(RarityId rarityId)
            throws InvocationTargetException, IllegalAccessException {
        ChangedRarityParam changedRarityParam = (ChangedRarityParam) changeRarityParamMethod.invoke(
                cardChanger, new RarityChangeableParam(rarityId, ChangeableParamType.RARITY));

        assertEquals(3, changedRarityParam
                .getOptions()
                .stream()
                .toList()
                .size());
        assertFalse(changedRarityParam
                .getOptions()
                .contains(rarityId));
    }
}
