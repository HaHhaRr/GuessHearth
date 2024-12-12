package ru.heart.guess.heartguess.controller.random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.heart.guess.heartguess.database.repository.CardRepository;

import java.util.Random;

@Component
public class RandomCardBuilder {

    @Autowired
    private CardRepository cardRepository;

    private Random random = new Random();

    private String getRandomType() {
        int randomCardTypeId = random.nextInt(5) + 1;

        return switch (randomCardTypeId) {
            case 1 -> "hero";
            case 2 -> "minion";
            case 3 -> "spell";
            case 4 -> "weapon";
            case 5 -> "location";
            default -> "";
        };
    }

    public int getRandomCard() {
        int heroCards = 49;
        int minionCards = 3734;
        int spellCards = 1797;
        int weaponCards = 193;
        int locationCards = 32;

        return switch (getRandomType()) {
            case "hero" -> {
                int randomHeroCard = random.nextInt(heroCards) + 1;
                yield cardRepository.getCardById(randomHeroCard, "hero");
            }
            case "minion" -> {
                int randomMinionCard = random.nextInt(minionCards) + 1;
                yield cardRepository.getCardById(randomMinionCard, "minion");
            }
            case "spell" -> {
                int randomSpellCard = random.nextInt(spellCards) + 1;
                yield cardRepository.getCardById(randomSpellCard, "spell");
            }
            case "weapon" -> {
                int randomWeaponCard = random.nextInt(weaponCards) + 1;
                yield cardRepository.getCardById(randomWeaponCard, "weapon");
            }
            case "location" -> {
                int randomLocationCard = random.nextInt(locationCards) + 1;
                yield cardRepository.getCardById(randomLocationCard, "location");
            }
            default -> 0;
        };
    }
}
