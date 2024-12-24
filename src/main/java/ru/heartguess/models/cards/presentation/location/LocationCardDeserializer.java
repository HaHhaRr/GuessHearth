package ru.heartguess.models.cards.presentation.location;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.heartguess.models.cards.RarityCardResolver;

import java.io.IOException;
import java.io.Serial;

@Component
public class LocationCardDeserializer extends StdDeserializer<LocationCardPresentation> {

    @Serial
    private static final long serialVersionUID = -692925375618452300L;

    @Autowired
    private RarityCardResolver rarityCardResolver;

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
        int durability = (Integer) node.get("health").numberValue();
        int manaCost = (Integer) node.get("manaCost").numberValue();
        String name = node.get("name").asText();
        String image = node.get("image").asText();
        return new LocationCardPresentation(rarityCardResolver.resolveRarityCard(rarityId),
                durability, manaCost, name, image);
    }
}
