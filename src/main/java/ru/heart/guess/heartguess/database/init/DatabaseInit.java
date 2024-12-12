package ru.heart.guess.heartguess.database.init;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInit {

    @Bean
    public ApplicationRunner creatingTableRunner() {
        return new DatabaseRunner();
    }
}
