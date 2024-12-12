package ru.heart.guess.heartguess.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.heart.guess.heartguess.controller.random.RandomCardBuilder;
import ru.heart.guess.heartguess.controller.wrapper.ObjectMapperWrapper;
import ru.heart.guess.heartguess.database.repository.CardRepository;
import ru.heart.guess.heartguess.models.cards.id.IdsList;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(path = "/", produces = "application/json")
public class CardDistributorController {

    @Autowired
    private ApiRequestHandler apiRequestHandler;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ObjectMapperWrapper objectMapper;

    @Autowired
    private RandomCardBuilder randomCardBuilder;

    private final String url = "https://eu.api.blizzard.com/hearthstone/cards";
    private final String locale = "?locale=ru_RU";

    @GetMapping("/card/specific")
    public String getSpecificCard(@RequestParam("set") String set,
                                  @RequestParam("className") String className,
                                  @RequestParam("rarity") String rarity,
                                  @RequestParam("type") String type) throws IOException {
        return apiRequestHandler.getCard(url + locale +
                "&set=" + set +
                "&class=" + className +
                "&rarity=" + rarity +
                "&type=" + type);
    }

    @GetMapping("/card/random")
    public String getRandomCard() throws IOException {
        return apiRequestHandler.getCard(url + "/" + randomCardBuilder.getRandomCard() + locale);
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
    public String saveCards(@PathVariable("cardType") String cardType) throws IOException {
        cardRepository.deleteData(cardType);

        int page = 1;
        int pageSize = 500;

        while (true) {
            IdsList idsList = objectMapper.readValue(apiRequestHandler
                            .getCard(url + locale +
                                    "&type=" + cardType +
                                    "&pageSize=" + pageSize +
                                    "&page=" + page),
                    IdsList.class);
            idsList.cardIds.forEach(s -> cardRepository.saveCardId(s.getCardId(), cardType));
            int totalPages = idsList.getPageCount();

            if (page == totalPages) {
                break;
            } else {
                page++;
            }
        }
        return "1";
    }
}
