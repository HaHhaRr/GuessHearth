package ru.heartguess.models.cards;

import org.springframework.stereotype.Component;
import ru.heartguess.models.RarityId;

import java.io.IOException;

@Component
public class RarityCardResolver {

    @SuppressWarnings("checkstyle:magicnumber")
    public RarityId resolveRarityCard(int rarityNum) throws IOException {
        return switch (rarityNum) {
            case 1 -> RarityId.COMMON;
            case 2 -> RarityId.BASE;
            case 3 -> RarityId.RARE;
            case 4 -> RarityId.EPIC;
            case 5 -> RarityId.LEGENDARY;
            default -> throw new IOException("Unknown rarity id");
        };
    }
}
