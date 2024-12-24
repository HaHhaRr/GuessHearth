package ru.heartguess.controller.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import ru.heartguess.oauth.OAuth2FlowHandler;

import java.io.IOException;
import java.util.Collections;

@Component
public class HttpEntityFactory {

    @Autowired
    private OAuth2FlowHandler oAuth2FlowHandler;

    public HttpEntity<String> create() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format("Bearer %s", oAuth2FlowHandler.getToken()));
        return new HttpEntity<>("parameters", headers);
    }
}
