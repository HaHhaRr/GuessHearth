package ru.heart.guess.heartguess.controller.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.heart.guess.heartguess.oauth.OAuth2FlowHandler;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class HttpEntityApiConfiguration {

    @Autowired
    private OAuth2FlowHandler oAuth2FlowHandler;

    @Bean
    public HttpEntity<String> httpEntityApi() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format("Bearer %s", oAuth2FlowHandler.getToken()));
        return new HttpEntity<>("parameters", headers);
    }
}
