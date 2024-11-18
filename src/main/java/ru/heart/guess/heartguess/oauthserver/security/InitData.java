package ru.heart.guess.heartguess.oauthserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Objects;
import java.util.UUID;

@Configuration
public class InitData {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner applicationRunner(RegisteredClientRepository registeredClientRepository) {
        return args -> {
            if (Objects.isNull(registeredClientRepository.findByClientId("test-client"))) {
                RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("test-client")
                        .clientSecret(passwordEncoder.encode("test-client"))
                        .redirectUri("http://localhost:8080/code")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .scope("login")
                        .scope("password")
                        .scope(OidcScopes.OPENID)
                        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                        .build();

                registeredClientRepository.save(registeredClient);
            }
        };
    }
}
