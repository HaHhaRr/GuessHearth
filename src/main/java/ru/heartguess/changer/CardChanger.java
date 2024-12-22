package ru.heartguess.changer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.heartguess.models.RarityId;
import ru.heartguess.models.cards.presentation.RarityCardResolver;
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;
import ru.heartguess.models.cards.presentation.minion.ChangedCard;

import java.io.IOException;
import java.util.Random;

@Service
public class CardChanger {

    @Autowired
    private RarityCardResolver rarityCardResolver;

    private Random random = new Random();

    public ChangedCard<?> change(MinionCardPresentation minionCardPresentation) throws IOException {
        int randomValue = random.nextInt(4);

        if (randomValue == 0) {
            return changeAttack(minionCardPresentation);
        } else if (randomValue == 1) {
            return changeHealth(minionCardPresentation);
        } else if (randomValue == 2) {
            return changeManaCost(minionCardPresentation);
        } else {
            return changeRarity(minionCardPresentation);
        }
    }

    private ChangedCard<Integer> changeAttack(MinionCardPresentation minionCardPresentation) {
        int originalAttack = minionCardPresentation.getAttack();
        int randomValue = random.nextInt(5) - 2;
        int newAttack = Math.max(originalAttack + randomValue, 0);

        if (newAttack == originalAttack) {
            newAttack++;
        }

        minionCardPresentation.setAttack(newAttack);
        return new ChangedCard<>(minionCardPresentation,
                ChangedCard.ChangeableParams.ATTACK,
                originalAttack);
    }

    private ChangedCard<Integer> changeHealth(MinionCardPresentation minionCardPresentation) {
        int originalHealth = minionCardPresentation.getHealth();
        int randomValue = random.nextInt(5) - 2;
        int newHealth = Math.max(originalHealth + randomValue, 0);

        if (newHealth == originalHealth || newHealth == 0) {
            newHealth++;
        }

        minionCardPresentation.setHealth(newHealth);
        return new ChangedCard<>(minionCardPresentation,
                ChangedCard.ChangeableParams.HEALTH,
                originalHealth);
    }

    private ChangedCard<Integer> changeManaCost(MinionCardPresentation minionCardPresentation) {
        int originalManaCost = minionCardPresentation.getManaCost();
        int randomValue = random.nextInt(5) - 2;
        int newManaCost = Math.max(originalManaCost + randomValue, 0);

        if (newManaCost == originalManaCost) {
            newManaCost++;
        }

        minionCardPresentation.setManaCost(newManaCost);
        return new ChangedCard<>(minionCardPresentation,
                ChangedCard.ChangeableParams.MANACOST,
                originalManaCost);
    }

    private ChangedCard<RarityId> changeRarity(
            MinionCardPresentation minionCardPresentation) throws IOException {

        int originalRarityId = rarityCardResolver.resolveIntValue(
                minionCardPresentation.getRarityId());
        int newRarityId;

        if (originalRarityId == 5) {
            newRarityId = random.nextInt(4) + 1;
        } else {
            newRarityId = random.nextInt(5) + 1;
        }

        if (newRarityId == originalRarityId) {
            newRarityId++;
        }

        minionCardPresentation.setRarityId(
                rarityCardResolver.resolveRarityCard(
                        newRarityId));
        return new ChangedCard<>(minionCardPresentation,
                ChangedCard.ChangeableParams.RARITY,
                rarityCardResolver.resolveRarityCard(originalRarityId));
    }
}