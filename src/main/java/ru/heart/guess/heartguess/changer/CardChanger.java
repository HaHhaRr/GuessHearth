package ru.heart.guess.heartguess.changer;

import ru.heart.guess.heartguess.models.cards.presentation.ChangedCard;
import ru.heart.guess.heartguess.models.cards.presentation.minion.MinionCardPresentation;

import java.util.Random;

public class CardChanger {

    static Random random = new Random();

    public static ChangedCard change(MinionCardPresentation minionCardPresentation) {
        int randomValue = random.nextInt(3);

        if (randomValue == 0) {
            return changeAttack(minionCardPresentation);
        } else if (randomValue == 1) {
            return changeHealth(minionCardPresentation);
        } else {
            return changeManaCost(minionCardPresentation);
        }
    }


    private static ChangedCard changeAttack(MinionCardPresentation minionCardPresentation) {
        int originalAttack = minionCardPresentation.getAttack();
        int randomValue = random.nextInt(5) - 2;
        int newAttack = Math.max(originalAttack + randomValue, 0);

        if (newAttack == originalAttack) {
            newAttack++;
        }

        minionCardPresentation.setAttack(newAttack);
        return new ChangedCard(minionCardPresentation, ChangedCard.ChangeableParams.ATTACK, originalAttack);
    }

    private static ChangedCard changeHealth(MinionCardPresentation minionCardPresentation) {
        int originalHealth = minionCardPresentation.getHealth();
        int randomValue = random.nextInt(5) - 2;
        int newHealth = Math.max(originalHealth + randomValue, 0);

        if (newHealth == originalHealth || newHealth == 0) {
            newHealth++;
        }

        minionCardPresentation.setHealth(newHealth);
        return new ChangedCard(minionCardPresentation, ChangedCard.ChangeableParams.HEALTH, originalHealth);
    }

    private static ChangedCard changeManaCost(MinionCardPresentation minionCardPresentation) {
        int originalManaCost = minionCardPresentation.getManaCost();
        int randomValue = random.nextInt(5) - 2;
        int newManaCost = Math.max(originalManaCost + randomValue, 0);

        if (newManaCost == originalManaCost) {
            newManaCost++;
        }

        minionCardPresentation.setManaCost(newManaCost);
        return new ChangedCard(minionCardPresentation, ChangedCard.ChangeableParams.MANACOST, originalManaCost);
    }
}