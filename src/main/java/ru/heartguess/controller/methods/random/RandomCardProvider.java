package ru.heartguess.controller.methods.random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.heartguess.controller.api.ApiRequestHandler;
import ru.heartguess.models.CardType;
import ru.heartguess.models.cards.presentation.root.CardPresentation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class RandomCardProvider {

    private final Random random = new Random();

    @Autowired
    private ApiRequestHandler apiRequestHandler;

    public CardPresentation getRandomCard() throws IOException {
        return apiRequestHandler.getCard(getRandomCardType());
    }

    private CardType getRandomCardType() {
        List<CardType> cardTypesList = Arrays.stream(CardType.values()).toList();
        return cardTypesList.get(random.nextInt(cardTypesList.size()));
    }
}
