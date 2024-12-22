package ru.heartguess.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.heartguess.controller.api.config.HttpEntityFactory;
import ru.heartguess.database.repository.CardRepository;
import ru.heartguess.models.CardType;
import ru.heartguess.models.cards.presentation.root.CardPresentation;
import ru.heartguess.models.cards.presentation.hero.HeroCardPresentation;
import ru.heartguess.models.cards.presentation.location.LocationCardPresentation;
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;
import ru.heartguess.models.cards.presentation.spell.SpellCardPresentation;
import ru.heartguess.models.cards.presentation.weapon.WeaponCardPresentation;

import java.io.IOException;

import static ru.heartguess.controller.utils.UrlBuilderUtils.BASE_URL;
import static ru.heartguess.controller.utils.UrlBuilderUtils.RU_LOCALE;

@Component
public class ApiRequestHandler {

    @Autowired
    private RestTemplate restTemplateApi;

    @Autowired
    private HttpEntityFactory httpEntityFactory;

    @Autowired
    private CardRepository cardRepository;

    public CardPresentation getCard(CardType cardType) throws IOException {
        return restTemplateApi
                .exchange(resolveUri(cardType),
                        HttpMethod.GET,
                        httpEntityFactory.create(),
                        resolveClassType(cardType))
                .getBody();
    }

    private Class<? extends CardPresentation> resolveClassType(CardType cardType) {
        return switch (cardType) {
            case CardType.HERO -> HeroCardPresentation.class;
            case CardType.LOCATION -> LocationCardPresentation.class;
            case CardType.MINION -> MinionCardPresentation.class;
            case CardType.SPELL -> SpellCardPresentation.class;
            case CardType.WEAPON -> WeaponCardPresentation.class;
        };
    }

    private String resolveUri(CardType cardType) {
        return BASE_URL +
                cardRepository.getRandomCardId(cardType) +
                "?locale=" + RU_LOCALE;
    }
}
