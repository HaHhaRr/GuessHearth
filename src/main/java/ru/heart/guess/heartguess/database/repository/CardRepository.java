package ru.heart.guess.heartguess.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.heart.guess.heartguess.models.CardType;
import ru.heart.guess.heartguess.models.cards.id.CardId;

import java.util.List;

@Repository
public class CardRepository {

    @Autowired
    @Qualifier("cardsJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void updateCardList(List<CardId> cardIdList, CardType cardType) {
        jdbcTemplate.execute("ALTER SEQUENCE " + cardType + "_id_seq RESTART WITH 1");
        jdbcTemplate.update("DELETE FROM " + cardType);

        cardIdList.forEach(cardId ->
                jdbcTemplate.update(
                        "INSERT INTO " + cardType + "(" + cardType + "_card_id) VALUES(?)",
                        cardId)
        );
    }

    @Transactional
    public int getCardById(int cardId, String cardType) {

        return jdbcTemplate.queryForObject("SELECT " + cardType + "_card_id FROM " + cardType + " WHERE ID = " + cardId
                , Integer.class);
    }
}
