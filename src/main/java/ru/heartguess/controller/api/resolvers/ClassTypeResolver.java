package ru.heartguess.controller.api.resolvers;

import org.springframework.stereotype.Component;
import ru.heartguess.models.CardType;
import ru.heartguess.models.cards.presentation.hero.HeroCardPresentation;
import ru.heartguess.models.cards.presentation.location.LocationCardPresentation;
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;
import ru.heartguess.models.cards.presentation.root.CardPresentation;
import ru.heartguess.models.cards.presentation.spell.SpellCardPresentation;
import ru.heartguess.models.cards.presentation.weapon.WeaponCardPresentation;

@Component
public class ClassTypeResolver {

    public Class<? extends CardPresentation> resolveClassType(CardType cardType) {
        return switch (cardType) {
            case CardType.HERO -> HeroCardPresentation.class;
            case CardType.LOCATION -> LocationCardPresentation.class;
            case CardType.MINION -> MinionCardPresentation.class;
            case CardType.SPELL -> SpellCardPresentation.class;
            case CardType.WEAPON -> WeaponCardPresentation.class;
        };
    }
}
