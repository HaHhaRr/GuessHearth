package ru.heart.guess.heartguess.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ClientInfo {

    @Value("${client-info.client_id}")
    private String CLIENT_ID;

    @Value("${client-info.client_secret}")
    private String CLIENT_SECRET;
}
