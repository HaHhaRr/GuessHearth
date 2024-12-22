package ru.heartguess.controller.methods.random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.heartguess.controller.api.ApiRequestHandler;
import ru.heartguess.models.CardType;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

import java.io.IOException;
import java.util.Random;

@Service
public class RandomCardProvider {

    @Autowired
    private ApiRequestHandler apiRequestHandler;

    public CardPresentation getRandomCard() throws IOException {
        return apiRequestHandler.getCard(getRandomCardType());
    }

    private CardType getRandomCardType() throws IOException {
        return switch (new Random().nextInt(5)) {
            case 0 -> CardType.HERO;
            case 1 -> CardType.MINION;
            case 2 -> CardType.SPELL;
            case 3 -> CardType.WEAPON;
            case 4 -> CardType.LOCATION;
            default -> throw new IOException("wrong card type number");
        };
    }
}
