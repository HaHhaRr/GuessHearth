package ru.heartguess.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;
    /**
     * Seconds from when received that the token will expire.
     */
    @JsonProperty("expires_in")
    private Long expiresIn;
}
