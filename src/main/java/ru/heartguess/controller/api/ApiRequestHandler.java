package ru.heartguess.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.heartguess.controller.api.config.HttpEntityFactory;
import ru.heartguess.controller.api.resolvers.ClassTypeResolver;
import ru.heartguess.database.repository.CardRepository;
import ru.heartguess.models.CardType;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

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

    @Autowired
    private ObjectMapper cardPresentationMapper;

    @Autowired
    private ClassTypeResolver classTypeResolver;

    public CardPresentation getCard(CardType cardType) throws IOException {

        String json = restTemplateApi
                .exchange(resolveUri(cardType),
                        HttpMethod.GET,
                        httpEntityFactory.create(),
                        String.class)
                .getBody();

        return cardPresentationMapper.readValue(json, classTypeResolver.resolveClassType(cardType));
    }

    private String resolveUri(CardType cardType) {
        return BASE_URL
                + cardRepository.getRandomCardId(cardType)
                + "?locale=" + RU_LOCALE;
    }
}
