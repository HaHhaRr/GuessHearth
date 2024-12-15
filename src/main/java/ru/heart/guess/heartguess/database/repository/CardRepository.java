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

    @Transactional
    public void updateCardList(List<CardId> cardIdList, String cardType) {
        jdbcTemplate.execute("ALTER SEQUENCE " + cardType + "_id_seq RESTART WITH 1");
        jdbcTemplate.update("DELETE FROM " + cardType);

        cardIdList.forEach(cardId ->
                jdbcTemplate.update(
                        "INSERT INTO " + cardType + "(" + cardType + "_card_id) VALUES(?)",
                        cardId.getCardId())
        );
    }

    @Transactional
    public List<String> getRandomCard(String cardType, int limit) {
        return jdbcTemplate.queryForList("SELECT " + cardType +
                        "_card_id FROM " + cardType +
                        " ORDER BY RANDOM() LIMIT " + limit,
                String.class);
    }
}
