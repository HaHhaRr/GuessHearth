/*package ru.heart.guess.heartguess.changer;

import ru.heart.guess.heartguess.models.cards.presentation.ChangedCard;
import ru.heart.guess.heartguess.models.cards.presentation.CardPresentation;

import java.util.Random;

public class CardChanger {

    static Random random = new Random();

    public static ChangedCard change(CardPresentation cardPresentation) {
        int randomValue = random.nextInt(3);

        if (randomValue == 0) {
            return changeAttack(cardPresentation);
        } else if (randomValue == 1) {
            return changeHealth(cardPresentation);
        } else {
            return changeManaCost(cardPresentation);
        }
    }


    private static ChangedCard changeAttack(CardPresentation cardPresentation) {
        int originalAttack = cardPresentation.getAttack();
        int randomValue = random.nextInt(5) - 2;
        int newAttack = Math.max(originalAttack + randomValue, 0);

        if (newAttack == originalAttack) {
            newAttack++;
        }

        cardPresentation.setAttack(newAttack);
        return new ChangedCard(cardPresentation, ChangedCard.ChangeableParams.ATTACK, originalAttack);
    }

    private static ChangedCard changeHealth(CardPresentation cardPresentation) {
        int originalHealth = cardPresentation.getHealth();
        int randomValue = random.nextInt(5) - 2;
        int newHealth = Math.max(originalHealth + randomValue, 0);

        if (newHealth == originalHealth || newHealth == 0) {
            newHealth++;
        }

        cardPresentation.setHealth(newHealth);
        return new ChangedCard(cardPresentation, ChangedCard.ChangeableParams.HEALTH, originalHealth);
    }

    private static ChangedCard changeManaCost(CardPresentation cardPresentation) {
        int originalManaCost = cardPresentation.getManaCost();
        int randomValue = random.nextInt(5) - 2;
        int newManaCost = Math.max(originalManaCost + randomValue, 0);

        if (newManaCost == originalManaCost) {
            newManaCost++;
        }

        cardPresentation.setManaCost(newManaCost);
        return new ChangedCard(cardPresentation, ChangedCard.ChangeableParams.MANACOST, originalManaCost);
    }
}
 */