package ru.heart.guess.heartguess.database.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import ru.heart.guess.heartguess.database.init.query.CreatingAuthDatabase;
import ru.heart.guess.heartguess.database.init.query.CreatingCardsDatabase;

public class DatabaseRunner implements ApplicationRunner {

    @Autowired
    private CreatingAuthDatabase creatingAuthDatabase;
    @Autowired
    private CreatingCardsDatabase creatingCardsDatabase;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        creatingAuthDatabase.createAuthTableQuery();
        creatingCardsDatabase.createCardsTableQuery();
    }
}
