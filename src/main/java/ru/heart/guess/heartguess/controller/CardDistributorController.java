package ru.heart.guess.heartguess.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.heart.guess.heartguess.controller.methods.random.RandomCardBuilder;
import ru.heart.guess.heartguess.controller.methods.save.CardTypeResolver;
import ru.heart.guess.heartguess.controller.methods.save.SaveCardsHandler;
import ru.heart.guess.heartguess.models.CardType;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

import java.io.IOException;
import java.util.List;

import static ru.heart.guess.heartguess.controller.config.WebClientConfig.BASE_URL;

@Slf4j
@RestController
@RequestMapping(path = "/", produces = "application/json")
public class CardDistributorController {

    private static final String RU_LOCALE = "ru_RU";

    @Autowired
    private ApiRequestHandler apiRequestHandler;

    @Autowired
    private SaveCardsHandler saveCardsHandler;

    @Autowired
    private CardTypeResolver cardTypeResolver;

    @Autowired
    private RandomCardBuilder randomCardBuilder;

    //    Нужна для разработки
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

    //    Нужна для разработки
    @GetMapping("/metadata")
    public String getMetadata() throws IOException {
        return apiRequestHandler.getCard("https://eu.api.blizzard.com/hearthstone/metadata?locale=" + RU_LOCALE);
    }

    //    Нужна для разработки
    @GetMapping("/get")
    public String get() throws IOException {
        return apiRequestHandler.getCard("https://eu.api.blizzard.com/hearthstone/cards/48146");
//                "locale=" + RU_LOCALE + "&type=hero&pageSize=500&page=1");
    }

    @GetMapping("/card/random")
    public List<String> randomCard(@RequestParam("limit") int limit) throws IOException {
        return randomCardBuilder.getRandomCardsList(limit);
    }

    @GetMapping("/save/{cardType}")
    public Mono<String> saveCards(@PathVariable("cardType") String cardTypeString) throws IOException {
        CardType cardType = cardTypeResolver.resolveCardType(cardTypeString);
        return saveCardsHandler.saveCards(cardType);
    }
}
