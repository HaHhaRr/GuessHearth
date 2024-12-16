package ru.heart.guess.heartguess.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.heart.guess.heartguess.controller.methods.random.RandomCardProvider;
import ru.heart.guess.heartguess.controller.methods.save.CardTypeResolver;
import ru.heart.guess.heartguess.controller.methods.save.SaveCardsHandler;
import ru.heart.guess.heartguess.models.CardType;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(path = "/", produces = "application/json")
public class CardDistributorController {

    @Autowired
    private SaveCardsHandler saveCardsHandler;

    @Autowired
    private CardTypeResolver cardTypeResolver;

    @Autowired
    private RandomCardProvider randomCardProvider;

    @GetMapping("/card/random")
    public String randomCard() throws IOException {
        return randomCardProvider.getRandomCard();
    }

    @GetMapping("/save/{cardType}")
    public Mono<String> saveCards(@PathVariable("cardType") String cardTypeString) throws IOException {
        CardType cardType = cardTypeResolver.resolveCardType(cardTypeString);
        return saveCardsHandler.saveCards(cardType);
    }
}
