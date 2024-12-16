package ru.heart.guess.heartguess.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.heart.guess.heartguess.oauth.OAuth2FlowHandler;

import java.io.IOException;
import java.util.Collections;

@Component
public class ApiRequestHandler {

    @Autowired
    private RestTemplate restTemplateApi;

    @Autowired
    private HttpEntity<String> httpEntityApi;

    //    TODO: Аргументом должен быть Type
    public String getCard(String url) {
        return restTemplateApi
                .exchange(url, HttpMethod.GET, httpEntityApi, String.class)
                .getBody();
    }
}
