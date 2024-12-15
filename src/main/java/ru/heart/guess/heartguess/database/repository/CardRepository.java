package ru.heart.guess.heartguess.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.heart.guess.heartguess.controller.methods.save.CardTypeResolver;
import ru.heart.guess.heartguess.database.config.JdbcTemplateQualifier;
import ru.heart.guess.heartguess.models.CardType;
import ru.heart.guess.heartguess.models.cards.id.CardId;

import java.util.List;

@Repository
public class CardRepository {

    @Autowired
    @Qualifier(JdbcTemplateQualifier.CARDS_JDBC_TEMPLATE)
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CardTypeResolver cardTypeResolver;

    @Transactional
    public void updateCardList(List<CardId> cardIdList, CardType cardType) {
        jdbcTemplate.execute("ALTER SEQUENCE " + cardTypeResolver.resolveStringValue(cardType) +
                "_id_seq RESTART WITH 1");
        jdbcTemplate.update("DELETE FROM " + cardTypeResolver.resolveStringValue(cardType));

        cardIdList.forEach(cardId ->
                jdbcTemplate.update(
                        "INSERT INTO " + cardTypeResolver.resolveStringValue(cardType) +
                                "(" + cardTypeResolver.resolveStringValue(cardType) + "_card_id) VALUES(?)",
                        cardId.getCardId())
        );
    }

    @Transactional
    public String getRandomCardId(CardType cardType) {
        return jdbcTemplate.queryForObject("SELECT " + cardTypeResolver.resolveStringValue(cardType) +
                        "_card_id FROM " + cardTypeResolver.resolveStringValue(cardType) +
                        " ORDER BY RANDOM() LIMIT 1",
                String.class);
    }
}
