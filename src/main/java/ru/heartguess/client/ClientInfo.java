package ru.heartguess.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ClientInfo {

    @Value("${client-info.client_id}")
    private String clientId;

    @Value("${client-info.client_secret}")
    private String clientSecret;
}
