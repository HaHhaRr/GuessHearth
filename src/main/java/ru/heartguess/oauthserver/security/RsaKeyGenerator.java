package ru.heartguess.oauthserver.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class RsaKeyGenerator {

    private static final int GENERATOR_KEY_SIZE = 2048;

    public static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(GENERATOR_KEY_SIZE);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}
