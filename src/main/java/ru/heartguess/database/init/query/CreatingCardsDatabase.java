package ru.heartguess.database.init.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.heartguess.database.config.JdbcTemplateQualifier;

@Component
public class CreatingCardsDatabase {

    @Autowired
    @Qualifier(JdbcTemplateQualifier.CARDS_JDBC_TEMPLATE)
    private JdbcTemplate jdbcTemplate;

    public void createCardsTableQuery() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS minion (
                ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                MINION_CARD_ID VARCHAR(45) NOT NULL
                );
                
                CREATE TABLE IF NOT EXISTS hero (
                ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                HERO_CARD_ID VARCHAR(45) NOT NULL
                );
                
                CREATE TABLE IF NOT EXISTS spell (
                ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                SPELL_CARD_ID VARCHAR(45) NOT NULL
                );
                
                CREATE TABLE IF NOT EXISTS weapon (
                ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                WEAPON_CARD_ID VARCHAR(45) NOT NULL
                );
                
                CREATE TABLE IF NOT EXISTS location (
                ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                LOCATION_CARD_ID VARCHAR(45) NOT NULL
                );
                """);
    }
}
