package ru.heart.guess.heartguess.schedule.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.heart.guess.heartguess.controller.methods.save.CardTypeResolver;
import ru.heart.guess.heartguess.controller.methods.save.SaveCardsHandler;
import ru.heart.guess.heartguess.models.CardType;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UpdateCardIds {

    @Autowired
    private CardTypeResolver cardTypeResolver;

    @Autowired
    private SaveCardsHandler saveCardsHandler;

    @Scheduled(fixedRate = 7, timeUnit = TimeUnit.DAYS)
    @Async
    public void updateCardIsd() {
        log.info("Start updating cards id data");

        List<CardType> cards = Arrays
                .stream(CardType.values())
                .toList();

        cards.forEach(cardType -> {
            log.info("Start updating {} table", cardTypeResolver.resolveStringValue(cardType));
            saveCardsHandler.saveCards(cardType).subscribe(log::info);
        });
    }
}
