package ru.heartguess.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.heartguess.changer.CardChanger;
import ru.heartguess.controller.methods.random.RandomCardProvider;
import ru.heartguess.controller.methods.save.CardTypeResolver;
import ru.heartguess.controller.methods.save.SaveCardsHandler;
import ru.heartguess.models.CardType;
import ru.heartguess.models.cards.presentation.ChangedCardPresentation;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(path = "/card", produces = "application/json")
public class CardDistributorController {

    @Autowired
    private SaveCardsHandler saveCardsHandler;

    @Autowired
    private CardTypeResolver cardTypeResolver;

    @Autowired
    private RandomCardProvider randomCardProvider;

    @Autowired
    private CardChanger cardChanger;

    @GetMapping("/random")
    public ChangedCardPresentation randomCard() throws IOException {
        CardPresentation cardPresentation = randomCardProvider.getRandomCard();
        return cardChanger.change(cardPresentation);
    }

    @GetMapping("/save/{cardType}")
    public Mono<String> saveCards(@PathVariable("cardType") String cardTypeString) throws IOException {
        CardType cardType = cardTypeResolver.resolveCardType(cardTypeString);
        return saveCardsHandler.saveCards(cardType);
    }
}
