package ru.heart.guess.heartguess.controller.methods.save;

import org.springframework.stereotype.Component;
import ru.heart.guess.heartguess.models.CardType;

import java.io.IOException;

@Component
public class CardTypeResolver {

    public CardType resolveCardType(String cardType) throws IOException {
        return switch (cardType) {
            case "hero" -> CardType.HERO;
            case "minion" -> CardType.MINION;
            case "weapon" -> CardType.WEAPON;
            case "location" -> CardType.LOCATION;
            case "spell" -> CardType.SPELL;
            default -> throw new IOException("unknown card type");
        };
    }

    public String resolveStringValue(CardType cardType) {
        return switch (cardType) {
            case CardType.HERO -> "hero";
            case CardType.LOCATION -> "location";
            case CardType.MINION -> "minion";
            case CardType.SPELL -> "spell";
            case CardType.WEAPON -> "weapon";
        };
    }
}
