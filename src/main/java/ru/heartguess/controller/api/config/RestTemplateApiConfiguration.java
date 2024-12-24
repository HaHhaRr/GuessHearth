package ru.heartguess.controller.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.heartguess.client.ClientInfo;

@Configuration
public class RestTemplateApiConfiguration {

    @Autowired
    private ClientInfo clientInfo;

    @Bean
    public RestTemplate restTemplateApi() {
        return new RestTemplateBuilder()
                .basicAuthentication(
                        clientInfo.getClientId(),
                        clientInfo.getClientSecret()
                ).build();
    }
}
