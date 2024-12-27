package ru.heartguess.changer.service;

import org.junit.jupiter.api.Test;
import ru.heartguess.changer.ChangeableParamType;
import ru.heartguess.changer.model.ChangeableParam;
import ru.heartguess.changer.model.NumericChangeableParam;
import ru.heartguess.changer.model.RarityChangeableParam;
import ru.heartguess.models.RarityId;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomChangeableParamSelectorTest {

    private ChangeableParamSelectorFactory changeableParamSelectorFactory = new ChangeableParamSelectorFactory();

    @Test
    void selectParamTest_NumericChangeableParam_ReturnNumericChangeableParam() {
        ChangeableParamSelector paramSelector = changeableParamSelectorFactory
                .create(new NumericChangeableParam(1, ChangeableParamType.MANACOST));
        ChangeableParam changeableParam = paramSelector
                .selectParam(Collections.emptyList());

        assertInstanceOf(NumericChangeableParam.class, changeableParam);
        NumericChangeableParam numericChangeableParam = (NumericChangeableParam) changeableParam;

        assertEquals(1, numericChangeableParam.getValue());
        assertEquals(ChangeableParamType.MANACOST, numericChangeableParam.getChangeableParamType());
    }

    @Test
    void selectParamTest_RarityChangeableParam_ReturnRarityChangeableParam() {
        ChangeableParamSelector paramSelector = changeableParamSelectorFactory
                .create(new RarityChangeableParam(RarityId.BASE, ChangeableParamType.RARITY));
        ChangeableParam changeableParam = paramSelector
                .selectParam(Collections.emptyList());

        assertInstanceOf(RarityChangeableParam.class, changeableParam);
        RarityChangeableParam rarityChangeableParam = (RarityChangeableParam) changeableParam;

        assertEquals(RarityId.BASE, rarityChangeableParam.getRarityId());
        assertEquals(ChangeableParamType.RARITY, rarityChangeableParam.getChangeableParamType());
    }
}

class ChangeableParamSelectorFactory {

    public ChangeableParamSelector create(ChangeableParam param) {
        return new ChangeableParamSelector() {
            @Override
            public ChangeableParam selectParam(List<ChangeableParam> params) {
                return param;
            }
        };
    }
}