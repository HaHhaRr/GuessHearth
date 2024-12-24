package ru.heartguess.controller.api.resolvers;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.heartguess.models.CardType;
import ru.heartguess.models.cards.presentation.hero.HeroCardDeserializer;
import ru.heartguess.models.cards.presentation.location.LocationCardDeserializer;
import ru.heartguess.models.cards.presentation.minion.MinionCardDeserializer;
import ru.heartguess.models.cards.presentation.spell.SpellCardDeserializer;
import ru.heartguess.models.cards.presentation.weapon.WeaponCardDeserializer;

@Component
public class DeserializerResolver {

    @Autowired
    private HeroCardDeserializer heroCardDeserializer;

    @Autowired
    private LocationCardDeserializer locationCardDeserializer;

    @Autowired
    private MinionCardDeserializer minionCardDeserializer;

    @Autowired
    private SpellCardDeserializer spellCardDeserializer;

    @Autowired
    private WeaponCardDeserializer weaponCardDeserializer;

    public StdDeserializer resolveDeserializer(CardType cardType) {
        return switch (cardType) {
            case CardType.HERO -> heroCardDeserializer;
            case CardType.LOCATION -> locationCardDeserializer;
            case CardType.MINION -> minionCardDeserializer;
            case CardType.SPELL -> spellCardDeserializer;
            case CardType.WEAPON -> weaponCardDeserializer;
        };
    }
}
