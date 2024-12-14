package ru.heart.guess.heartguess.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.heart.guess.heartguess.client.ClientInfo;
import ru.heart.guess.heartguess.controller.random.RandomCardBuilder;
import ru.heart.guess.heartguess.controller.wrapper.ObjectMapperWrapper;
import ru.heart.guess.heartguess.database.repository.CardRepository;
import ru.heart.guess.heartguess.models.CardType;
import ru.heart.guess.heartguess.models.PagesCountResponse;
import ru.heart.guess.heartguess.models.cards.id.CardId;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/", produces = "application/json")
public class CardDistributorController {
    private static final String BASE_URL = "https://eu.api.blizzard.com/hearthstone/cards";
    private static final String RU_LOCALE = "ru_RU";
    private static final int RETRY_COUNT = 3;
    private static final int PAGE_SIZE = 500;

    private final WebClient webClient = WebClient.create(BASE_URL);

    @Autowired
    private ApiRequestHandler apiRequestHandler;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ObjectMapperWrapper objectMapper;

    @Autowired
    private RandomCardBuilder randomCardBuilder;

    @Autowired
    private ClientInfo clientInfo;

    @GetMapping("/card/specific")
    public String getSpecificCard(@RequestParam("set") String set,
                                  @RequestParam("className") String className,
                                  @RequestParam("rarity") String rarity,
                                  @RequestParam("type") String type) throws IOException {
        return apiRequestHandler.getCard(BASE_URL +
                "?locale=" + RU_LOCALE +
                "&set=" + set +
                "&class=" + className +
                "&rarity=" + rarity +
                "&type=" + type);
    }

    @GetMapping("/card/random")
    public String getRandomCard() throws IOException {
       throw new IOException("Not yet implemented");
    }

    //    Нужна для разработки
    @GetMapping("/metadata")
    public String getMetadata() throws IOException {
        return apiRequestHandler.getCard("https://eu.api.blizzard.com/hearthstone/metadata?locale=ru_RU");
    }

    //    Нужна для разработки
    @GetMapping("/get")
    public String get() throws IOException {
        return apiRequestHandler.getCard("https://eu.api.blizzard.com/hearthstone/cards?" +
                "locale=ru_RU&type=minion&pageSize=1000&page=10");
    }

    @GetMapping("/save/{cardType}")
    public Mono<String> saveCards(@PathVariable("cardType") String cardTypeString) throws IOException {
        CardType cardType = resolveCardType(cardTypeString);
        return getCards(cardType)
                .map(cardIds -> {
                    cardRepository.updateCardList(cardIds, cardType);
                    return "Successfully updated card list with type " + cardTypeString;
                })
                .onErrorReturn("Error occurred while updating card list with type " + cardTypeString);
    }

    private CardType resolveCardType(String cardType) throws IOException {
        return switch (cardType) {
            case "hero" -> CardType.HERO;
            case "minion" -> CardType.MINION;
            case "weapon" -> CardType.WEAPON;
            case "location" -> CardType.LOCATION;
            case "spell" -> CardType.SPELL;
            default -> throw new IOException("unknown card type");
        };
    }

    private String resolveStringValue(CardType cardType) {
        return switch (cardType) {
            case CardType.HERO -> "hero";
            case CardType.LOCATION -> "location";
            case CardType.MINION -> "minion";
            case CardType.SPELL -> "spell";
            case CardType.WEAPON -> "weapon";
        };
    }

    private Mono<List<CardId>> getCards(CardType cardType) {
        return getTotalPages(cardType)
                .flatMapMany(totalPages -> Flux.range(1, totalPages))
                .flatMap(page -> getPageData(cardType, page))
                .collectList();
    }

    private Flux<CardId> getPageData(CardType cardType, int page) {
        return webClient.get()
                .uri("?locale={locale}&type={type}&pageSize={pageSize}&page={page}",
                        RU_LOCALE, resolveStringValue(cardType), PAGE_SIZE, page)
                .headers(headers -> headers.setBasicAuth(
                        clientInfo.getClientId(), clientInfo.getClientSecret()))
                .retrieve()
                .bodyToFlux(CardId.class)
                .retry(RETRY_COUNT);
    }

    private Mono<Integer> getTotalPages(CardType cardType) {
        return webClient.get()
                .uri("?locale={locale}&type={type}&pageSize={pageSize}&page={page}",
                        RU_LOCALE, resolveStringValue(cardType), PAGE_SIZE, 1)
                .headers(headers -> headers.setBasicAuth(
                        clientInfo.getClientId(), clientInfo.getClientSecret()))
                .retrieve()
                .bodyToMono(PagesCountResponse.class)
                .retry(RETRY_COUNT)
                .map(PagesCountResponse::getPageCount);
    }
}
