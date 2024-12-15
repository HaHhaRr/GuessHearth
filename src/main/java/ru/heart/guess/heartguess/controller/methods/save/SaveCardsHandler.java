package ru.heart.guess.heartguess.controller.methods.save;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.heart.guess.heartguess.database.repository.CardRepository;
import ru.heart.guess.heartguess.models.CardType;
import ru.heart.guess.heartguess.models.PagesCountResponse;
import ru.heart.guess.heartguess.models.cards.CardsPageResponse;
import ru.heart.guess.heartguess.models.cards.id.CardId;
import ru.heart.guess.heartguess.oauth.OAuth2FlowHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaveCardsHandler {

    @Autowired
    private OAuth2FlowHandler oAuth2FlowHandler;

    @Autowired
    private WebClient webClient;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardTypeResolver cardTypeResolver;

    private static final int RETRY_COUNT = 3;
    private static final int PAGE_SIZE = 500;
    private static final String RU_LOCALE = "ru_RU";
    private static final String SUCCESS_UPDATE_MESSAGE = "Successfully updated card list with type ";
    private static final String ERROR_UPDATE_MESSAGE = "Error occurred while updating card list with type ";

    public Mono<String> saveCards(CardType cardType) {
        return getCards(cardType)
                .map(cardIds -> {
                    cardRepository.updateCardList(cardIds, cardTypeResolver.resolveStringValue(cardType));
                    return SUCCESS_UPDATE_MESSAGE + cardTypeResolver.resolveStringValue(cardType);
                })
                .onErrorReturn(ERROR_UPDATE_MESSAGE + cardTypeResolver.resolveStringValue(cardType));
    }

    private Mono<List<CardId>> getCards(CardType cardType) {
        return getTotalPages(cardType)
                .flatMapMany(totalPages -> Flux.range(1, totalPages))
                .flatMap(page -> resolveNonPlayableHero(cardType,
                        getPageData(cardType, page)))
                .collectList()
                .map(lists -> lists.stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()));
    }

    private Mono<List<CardId>> getPageData(CardType cardType, int page) {
        try {
            String token = oAuth2FlowHandler.getToken();
            return webClient.get()
                    .uri("?locale={locale}&type={type}&pageSize={pageSize}&page={page}",
                            RU_LOCALE, cardTypeResolver.resolveStringValue(cardType), PAGE_SIZE, page)
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(CardsPageResponse.class)
                    .map(CardsPageResponse::getCardIds)
                    .retry(RETRY_COUNT);
        } catch (IOException e) {
            return Mono.empty();
        }
    }

    private Mono<Integer> getTotalPages(CardType cardType) {
        try {
            String token = oAuth2FlowHandler.getToken();
            return webClient.get()
                    .uri("?locale={locale}&type={type}&pageSize={pageSize}&page={page}",
                            RU_LOCALE, cardTypeResolver.resolveStringValue(cardType), PAGE_SIZE, 1)
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(PagesCountResponse.class)
                    .retry(RETRY_COUNT)
                    .map(PagesCountResponse::getPageCount);
        } catch (IOException e) {
            return Mono.empty();
        }
    }

    private Mono<List<CardId>> resolveNonPlayableHero(CardType cardType,
                                                      Mono<List<CardId>> cardIdListMono) {
        if (!cardType.equals(CardType.HERO)) {
            return cardIdListMono;
        }
        return cardIdListMono.map(list ->
                list.stream()
                        .filter(cardId ->
                                cardId.getManaCost() != 0)
                        .collect(Collectors.toList()));
    }
}
