package ru.heartguess.models.cards.presentation.hero;

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
public class HeroCardDeserializer extends StdDeserializer<HeroCardPresentation> {

    @Serial
    private static final long serialVersionUID = 5305327781634853351L;

    @Autowired
    private RarityCardResolver rarityCardResolver;

    public HeroCardDeserializer() {
        this(null);
    }

    protected HeroCardDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HeroCardPresentation deserialize(JsonParser jsonParser,
                                             DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int rarityId = (Integer) node.get("rarityId").numberValue();
        int manaCost = (Integer) node.get("manaCost").numberValue();
        String name = node.get("name").asText();
        String image = node.get("image").asText();
        return new HeroCardPresentation(rarityCardResolver.resolveRarityCard(rarityId), manaCost, name, image);
    }
}
