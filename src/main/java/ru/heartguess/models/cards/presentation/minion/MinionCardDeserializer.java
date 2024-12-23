package ru.heartguess.models.cards.presentation.minion;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.heartguess.models.cards.RarityCardResolver;
import ru.heartguess.models.cards.presentation.hero.HeroCardPresentation;

import java.io.IOException;
import java.io.Serial;

public class MinionCardDeserializer extends StdDeserializer<MinionCardPresentation> {

    @Serial
    private static final long serialVersionUID = -1539448585474444449L;

    private RarityCardResolver rarityCardResolver = new RarityCardResolver();

    public MinionCardDeserializer() {
        this(null);
    }

    protected MinionCardDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MinionCardPresentation deserialize(JsonParser jsonParser,
                                              DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int attack = (Integer) node.get("attack").numberValue();
        int health = (Integer) node.get("health").numberValue();
        int manaCost = (Integer) node.get("manaCost").numberValue();
        int rarityId = (Integer) node.get("rarityId").numberValue();
        String name = node.get("name").asText();
        String image = node.get("image").asText();
        return new MinionCardPresentation(attack, health, manaCost,
                rarityCardResolver.resolveRarityCard(rarityId), name, image);
    }
}