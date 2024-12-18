package ru.heart.guess.heartguess.oauthserver.security.data;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.heart.guess.heartguess.oauthserver.security.data.runner.AuthAppRunner;
import ru.heart.guess.heartguess.oauthserver.security.data.runner.UserAppRunner;

@Configuration
public class InitDataConfig {

    @Bean
    public ApplicationRunner authDataRunner() {
        return new AuthAppRunner();
    }

    @Bean
    public ApplicationRunner userDataRunner() {
        return new UserAppRunner();
    }
}
