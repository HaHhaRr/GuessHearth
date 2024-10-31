package ru.heart.guess.heartguess.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.heart.guess.heartguess.client.ClientInfo;
import ru.heart.guess.heartguess.config.AppConfig;
import ru.heart.guess.heartguess.models.TokenResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Base64;

/**
 * {@inheritDoc}
 */
@Service
public class OAuth2FlowHandler {
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private ObjectMapper objectMapper;

    private String token = null;
    private Instant tokenExpiry = null; // Instant when the token will expire

    private final Object tokenLock = new Object();

    public String getToken() throws IOException {
        if (isTokenInvalid()) {
            String encodedCredentials =
                    Base64.getEncoder()
                            .encodeToString(
                                    String.format(
                                                    "%s:%s",
                                                    ClientInfo.CLIENT_ID,
                                                    ClientInfo.CLIENT_SECRET
                                            )
                                            .getBytes(appConfig.getEncoding()));

            HttpURLConnection con = null;

            try {
                URL url = new URL(appConfig.getTokenUrl(), "");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", String.format("Basic %s", encodedCredentials));
                con.setDoOutput(true);
                con.getOutputStream().write("grant_type=client_credentials".getBytes(appConfig.getEncoding()));

                String response = IOUtils.toString(con.getInputStream(), appConfig.getEncoding());

                // Reads the JSON response and converts it to TokenResponse class or throws an exception
                TokenResponse tokenResponse = objectMapper.readValue(response, TokenResponse.class);
                synchronized (tokenLock) {
                    tokenExpiry = Instant.now().plusSeconds(tokenResponse.getExpires_in());
                    token = tokenResponse.getAccess_token();
                }

            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
        }
        synchronized (tokenLock) {
            return token;
        }
    }

    public boolean isTokenInvalid() {
        synchronized (tokenLock) {
            if (token == null) {
                return true;
            }
            if (tokenExpiry == null) {
                return true;
            }
            return Instant.now().isAfter(tokenExpiry);
        }
    }
}