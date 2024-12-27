package ru.heartguess.changer.service;

import org.springframework.stereotype.Component;
import ru.heartguess.changer.ChangeableParamType;
import ru.heartguess.changer.model.NumericChangeableParam;
import ru.heartguess.changer.model.RarityChangeableParam;
import ru.heartguess.changer.model.ChangeableParam;
import ru.heartguess.models.cards.presentation.hero.HeroCardPresentation;
import ru.heartguess.models.cards.presentation.location.LocationCardPresentation;
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;
import ru.heartguess.models.cards.presentation.root.CardPresentation;
import ru.heartguess.models.cards.presentation.spell.SpellCardPresentation;
import ru.heartguess.models.cards.presentation.weapon.WeaponCardPresentation;

import java.util.List;

@Component
public class ChangeableParamsResolver {

    public List<ChangeableParam> resolveChangeableParams(CardPresentation cardPresentation) {
        return switch (cardPresentation) {
            case MinionCardPresentation minion -> List.of(
                    new NumericChangeableParam(minion.getAttack(), ChangeableParamType.ATTACK),
                    new NumericChangeableParam(minion.getHealth(), ChangeableParamType.HEALTH),
                    new NumericChangeableParam(minion.getManaCost(), ChangeableParamType.MANACOST),
                    new RarityChangeableParam(minion.getRarityId(), ChangeableParamType.RARITY)
            );
            case HeroCardPresentation hero -> List.of(
                    new NumericChangeableParam(hero.getManaCost(), ChangeableParamType.MANACOST),
                    new RarityChangeableParam(hero.getRarityId(), ChangeableParamType.RARITY)
            );
            case LocationCardPresentation location -> List.of(
                    new NumericChangeableParam(location.getManaCost(), ChangeableParamType.MANACOST),
                    new NumericChangeableParam(location.getDurability(), ChangeableParamType.DURABILITY),
                    new RarityChangeableParam(location.getRarityId(), ChangeableParamType.RARITY)
            );
            case SpellCardPresentation spell -> List.of(
                    new NumericChangeableParam(spell.getManaCost(), ChangeableParamType.MANACOST),
                    new RarityChangeableParam(spell.getRarityId(), ChangeableParamType.RARITY)
            );
            case WeaponCardPresentation weapon -> List.of(
                    new NumericChangeableParam(weapon.getAttack(), ChangeableParamType.ATTACK),
                    new NumericChangeableParam(weapon.getDurability(), ChangeableParamType.DURABILITY),
                    new NumericChangeableParam(weapon.getManaCost(), ChangeableParamType.MANACOST),
                    new RarityChangeableParam(weapon.getRarityId(), ChangeableParamType.RARITY)
            );
            default -> throw new IllegalStateException("Unknown cardPresentation value: " + cardPresentation);
        };
    }
}
