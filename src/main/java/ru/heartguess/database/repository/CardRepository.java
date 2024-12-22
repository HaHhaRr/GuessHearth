package ru.heartguess.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.heartguess.controller.methods.save.CardTypeResolver;
import ru.heartguess.database.config.JdbcTemplateQualifier;
import ru.heartguess.models.CardType;
import ru.heartguess.models.cards.id.CardId;

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
        String cardTypeName = cardTypeResolver.resolveStringValue(cardType);

        jdbcTemplate.execute("ALTER SEQUENCE " + cardTypeName +
                "_id_seq RESTART WITH 1");
        jdbcTemplate.update("DELETE FROM " + cardTypeName);

        cardIdList.forEach(cardId ->
                jdbcTemplate.update(
                        "INSERT INTO " + cardTypeName +
                                "(" + cardTypeName + "_card_id) VALUES(?)",
                        cardId.getCardId())
        );
    }

    @Transactional
    public String getRandomCardId(CardType cardType) {
        String cardTypeName = cardTypeResolver.resolveStringValue(cardType);

        return jdbcTemplate.queryForObject("SELECT " + cardTypeName +
                        "_card_id FROM " + cardTypeName +
                        " ORDER BY RANDOM() LIMIT 1",
                String.class);
    }
}
