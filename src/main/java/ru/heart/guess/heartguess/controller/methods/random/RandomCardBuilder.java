package ru.heart.guess.heartguess.controller.methods.random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.heart.guess.heartguess.controller.ApiRequestHandler;
import ru.heart.guess.heartguess.controller.methods.save.CardTypeResolver;
import ru.heart.guess.heartguess.database.repository.CardRepository;
import ru.heart.guess.heartguess.models.CardType;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

import static ru.heart.guess.heartguess.controller.config.WebClientConfig.BASE_URL;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class RandomCardBuilder {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ApiRequestHandler apiRequestHandler;

    @Autowired
    private CardTypeResolver cardTypeResolver;

    private static final String RU_LOCALE = "ru_RU";

    public List<String> getRandomCardsList(int limit) throws IOException {
        List<String> cardsIdList = cardRepository.getRandomCard
                (cardTypeResolver.resolveStringValue(resolveRandomCardType()), limit);
        return cardsIdList.stream().map(cardId -> {
            try {
                return apiRequestHandler.getCard(BASE_URL + cardId +
                        "?locale=" + RU_LOCALE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    private CardType resolveRandomCardType() throws IOException {
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
