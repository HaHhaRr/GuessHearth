package ru.heartguess.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * POJO for reading in the oAuth2 access token response from <a href="https://_region_.battle.net/oauth/token">...</a> .
 */
@JsonIgnoreProperties(value = "sub")
@Data
public class TokenResponse {
    /**
     * The access token used on future requests to the API.
     */
    private String accessToken;
    private String tokenType;
    /**
     * Seconds from when received that the token will expire.
     */
    private Long expiresIn;
}
