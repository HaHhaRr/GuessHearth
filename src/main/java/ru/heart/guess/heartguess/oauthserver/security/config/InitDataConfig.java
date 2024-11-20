package ru.heart.guess.heartguess.oauthserver.security.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.heart.guess.heartguess.oauthserver.security.AppRunner;

@Configuration
public class InitDataConfig {

    @Bean
    public ApplicationRunner applicationRunner() {
        return new AppRunner();
    }
}
