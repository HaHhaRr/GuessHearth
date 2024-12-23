package ru.heartguess.models.cards.presentation.location;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.heartguess.models.cards.RarityCardResolver;

import java.io.IOException;
import java.io.Serial;

public class LocationCardDeserializer extends StdDeserializer<LocationCardPresentation> {

    @Serial
    private static final long serialVersionUID = -692925375618452300L;

    private RarityCardResolver rarityCardResolver = new RarityCardResolver();

    public LocationCardDeserializer() {
        this(null);
    }

    protected LocationCardDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocationCardPresentation deserialize(JsonParser jsonParser,
                                                DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int rarityId = (Integer) node.get("rarityId").numberValue();
        int health = (Integer) node.get("health").numberValue();
        int manaCost = (Integer) node.get("manaCost").numberValue();
        String name = node.get("name").asText();
        String image = node.get("image").asText();
        return new LocationCardPresentation(rarityCardResolver.resolveRarityCard(rarityId),
                health, manaCost, name, image);
    }
}
