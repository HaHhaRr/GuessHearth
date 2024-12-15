package ru.heart.guess.heartguess.controller.methods.random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.heart.guess.heartguess.controller.ApiRequestHandler;
import ru.heart.guess.heartguess.controller.methods.save.CardTypeResolver;
import ru.heart.guess.heartguess.database.repository.CardRepository;
import ru.heart.guess.heartguess.models.CardType;

import java.io.IOException;
import java.util.Random;

import static ru.heart.guess.heartguess.controller.utils.UrlBuilderUtils.BASE_URL;
import static ru.heart.guess.heartguess.controller.utils.UrlBuilderUtils.RU_LOCALE;

@Service
public class RandomCardBuilder {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ApiRequestHandler apiRequestHandler;

    public String getRandomCard() throws IOException {
        return apiRequestHandler.getCard(BASE_URL +
                cardRepository.getRandomCardId(getRandomCardType()) +
                "?locale=" + RU_LOCALE);
    }

    private CardType getRandomCardType() throws IOException {
        return switch (new Random().nextInt(5)) {
            case 0 -> CardType.HERO;
            case 1 -> CardType.MINION;
            case 2 -> CardType.SPELL;
            case 3 -> CardType.WEAPON;
            case 4 -> CardType.LOCATION;
            default -> throw new IOException("wrong number");
        };
    }
}
