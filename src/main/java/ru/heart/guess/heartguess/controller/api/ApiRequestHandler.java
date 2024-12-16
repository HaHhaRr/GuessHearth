package ru.heart.guess.heartguess.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.heart.guess.heartguess.controller.api.config.HttpEntityFactoryApi;

import java.io.IOException;

@Component
public class ApiRequestHandler {

    @Autowired
    private RestTemplate restTemplateApi;

    @Autowired
    private HttpEntityFactoryApi httpEntityFactoryApi;

    //    TODO: Аргументом должен быть Type
    public String getCard(String url) throws IOException {
        return restTemplateApi
                .exchange(url, HttpMethod.GET, httpEntityFactoryApi.create(), String.class)
                .getBody();
    }
}
