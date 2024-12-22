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
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;
import ru.heartguess.models.cards.presentation.minion.ChangedCard;
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

    @GetMapping("/random")
    public CardPresentation randomCard() throws IOException {
        return randomCardProvider.getRandomCard();
    }

    @GetMapping("/save/{cardType}")
    public Mono<String> saveCards(@PathVariable("cardType") String cardTypeString) throws IOException {
        CardType cardType = cardTypeResolver.resolveCardType(cardTypeString);
        return saveCardsHandler.saveCards(cardType);
    }
}
