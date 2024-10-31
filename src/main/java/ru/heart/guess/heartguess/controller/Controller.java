package ru.heart.guess.heartguess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.heart.guess.heartguess.client.ClientInfo;
import ru.heart.guess.heartguess.oauth.OAuth2FlowHandler;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping(path = "/", produces = "application/json")
public class Controller {

    @Autowired
    OAuth2FlowHandler oAuth2FlowHandler;

    @GetMapping("card")
    public String randomCard(@RequestParam int cardId) throws IOException {
        RestTemplate restTemplate =
                new RestTemplateBuilder()
                        .basicAuthentication(
                                ClientInfo.CLIENT_ID,
                                ClientInfo.CLIENT_SECRET
                        ).build();

        String token = oAuth2FlowHandler.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format("Bearer %s", token));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        return restTemplate.exchange(constructCardUrl(cardId), HttpMethod.GET, entity, String.class).getBody();
    }

    private String constructCardUrl(int cardId) {
        return "https://eu.api.blizzard.com/hearthstone/cards/" + cardId + "?locale=ru_RU";
    }
}