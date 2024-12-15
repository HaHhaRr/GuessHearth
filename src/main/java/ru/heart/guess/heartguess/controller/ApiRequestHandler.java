package ru.heart.guess.heartguess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.heart.guess.heartguess.client.ClientInfo;
import ru.heart.guess.heartguess.oauth.OAuth2FlowHandler;

import java.io.IOException;
import java.util.Collections;

@Component
public class ApiRequestHandler {

    @Autowired
    private OAuth2FlowHandler oAuth2FlowHandler;

    @Autowired
    private ClientInfo clientInfo;

    //    TODO: Аргументом должен быть Type
    public String getCard(String url) throws IOException {
        return getRestTemplateApi()
                .exchange(url, HttpMethod.GET, getHttpEntityApi(), String.class)
                .getBody();
    }

    private RestTemplate getRestTemplateApi() {
        return new RestTemplateBuilder()
                .basicAuthentication(
                        clientInfo.getClientId(),
                        clientInfo.getClientSecret()
                ).build();
    }

    private HttpEntity<String> getHttpEntityApi() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format("Bearer %s", oAuth2FlowHandler.getToken()));
        return new HttpEntity<>("parameters", headers);
    }
}
