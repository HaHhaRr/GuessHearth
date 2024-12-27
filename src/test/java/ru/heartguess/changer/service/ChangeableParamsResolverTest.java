package ru.heartguess.changer.service;

import org.junit.jupiter.api.Test;
import ru.heartguess.changer.ChangeableParamType;
import ru.heartguess.changer.model.ChangeableParam;
import ru.heartguess.changer.model.NumericChangeableParam;
import ru.heartguess.changer.model.RarityChangeableParam;
import ru.heartguess.models.cards.presentation.hero.HeroCardPresentation;
import ru.heartguess.models.cards.presentation.location.LocationCardPresentation;
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;
import ru.heartguess.models.cards.presentation.root.CardPresentation;
import ru.heartguess.models.cards.presentation.spell.SpellCardPresentation;
import ru.heartguess.models.cards.presentation.weapon.WeaponCardPresentation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChangeableParamsResolverTest {

    private ChangeableParamsResolver changeableParamsResolver = new ChangeableParamsResolver();

    @Test
    void resolveChangeableParamsTest_ReturnListSizeEqualsFourAndChangeableParamListForMinion() {
        MinionCardPresentation minion = new MinionCardPresentation();
        List<ChangeableParam> changeableParams = changeableParamsResolver.resolveChangeableParams(minion);

        assertEquals(4, changeableParams.size());
        assertTrue(changeableParams.contains(new NumericChangeableParam(0, ChangeableParamType.ATTACK)));
        assertTrue(changeableParams.contains(new NumericChangeableParam(0, ChangeableParamType.HEALTH)));
        assertTrue(changeableParams.contains(new NumericChangeableParam(0, ChangeableParamType.MANACOST)));
        assertTrue(changeableParams.contains(new RarityChangeableParam(null, ChangeableParamType.RARITY)));
    }

    @Test
    void resolveChangeableParamsTest_ReturnListSizeEqualsTwoAndChangeableParamListForHero() {
        HeroCardPresentation hero = new HeroCardPresentation();
        List<ChangeableParam> changeableParams = changeableParamsResolver.resolveChangeableParams(hero);

        assertEquals(2, changeableParams.size());
        assertTrue(changeableParams.contains(new NumericChangeableParam(0, ChangeableParamType.MANACOST)));
        assertTrue(changeableParams.contains(new RarityChangeableParam(null, ChangeableParamType.RARITY)));
    }

    @Test
    void resolveChangeableParamsTest_ReturnListSizeEqualsThreeAndChangeableParamListForLocation() {
        LocationCardPresentation location = new LocationCardPresentation();
        List<ChangeableParam> changeableParams = changeableParamsResolver.resolveChangeableParams(location);

        assertEquals(3, changeableParams.size());
        assertTrue(changeableParams.contains(new NumericChangeableParam(0, ChangeableParamType.DURABILITY)));
        assertTrue(changeableParams.contains(new NumericChangeableParam(0, ChangeableParamType.MANACOST)));
        assertTrue(changeableParams.contains(new RarityChangeableParam(null, ChangeableParamType.RARITY)));
    }

    @Test
    void resolveChangeableParamsTest_ReturnListSizeEqualsTwoAndChangeableParamListForSpell() {
        SpellCardPresentation spell = new SpellCardPresentation();
        List<ChangeableParam> changeableParams = changeableParamsResolver.resolveChangeableParams(spell);

        assertEquals(2, changeableParams.size());
        assertTrue(changeableParams.contains(new NumericChangeableParam(0, ChangeableParamType.MANACOST)));
        assertTrue(changeableParams.contains(new RarityChangeableParam(null, ChangeableParamType.RARITY)));
    }

    @Test
    void resolveChangeableParamsTest_ReturnListSizeEqualsFourAndChangeableParamListForWeapon() {
        WeaponCardPresentation weapon = new WeaponCardPresentation();
        List<ChangeableParam> changeableParams = changeableParamsResolver.resolveChangeableParams(weapon);

        assertEquals(4, changeableParams.size());
        assertTrue(changeableParams.contains(new NumericChangeableParam(0, ChangeableParamType.ATTACK)));
        assertTrue(changeableParams.contains(new NumericChangeableParam(0, ChangeableParamType.DURABILITY)));
        assertTrue(changeableParams.contains(new NumericChangeableParam(0, ChangeableParamType.MANACOST)));
        assertTrue(changeableParams.contains(new RarityChangeableParam(null, ChangeableParamType.RARITY)));
    }

    @Test
    void resolveChangeableParamsTest_ReturnIllegalStateException() {
        CardPresentation cardPresentation = new CardPresentation() {
        };

        assertThrows(IllegalStateException.class, () -> changeableParamsResolver
                .resolveChangeableParams(cardPresentation));
    }
}

