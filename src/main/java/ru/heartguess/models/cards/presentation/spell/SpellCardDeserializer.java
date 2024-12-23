package ru.heartguess.models.cards.presentation.spell;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.heartguess.models.cards.RarityCardResolver;

import java.io.IOException;
import java.io.Serial;

public class SpellCardDeserializer extends StdDeserializer<SpellCardPresentation> {

    @Serial
    private static final long serialVersionUID = -7846498488686715710L;

    private RarityCardResolver rarityCardResolver = new RarityCardResolver();

    public SpellCardDeserializer() {
        this(null);
    }

    protected SpellCardDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SpellCardPresentation deserialize(JsonParser jsonParser,
                                             DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int rarityId = (Integer) node.get("rarityId").numberValue();
        int manaCost = (Integer) node.get("manaCost").numberValue();
        String name = node.get("name").asText();
        String image = node.get("image").asText();
        return new SpellCardPresentation(rarityCardResolver.resolveRarityCard(rarityId), manaCost, name, image);
    }
}
