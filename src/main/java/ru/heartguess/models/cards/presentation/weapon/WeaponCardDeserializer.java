package ru.heartguess.models.cards.presentation.weapon;

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
public class WeaponCardDeserializer extends StdDeserializer<WeaponCardPresentation> {

    @Serial
    private static final long serialVersionUID = -2607573427142203690L;

    @Autowired
    private RarityCardResolver rarityCardResolver;

    public WeaponCardDeserializer() {
        this(null);
    }

    protected WeaponCardDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public WeaponCardPresentation deserialize(JsonParser jsonParser,
                                              DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int attack = (Integer) node.get("attack").numberValue();
        int durability = (Integer) node.get("durability").numberValue();
        int manaCost = (Integer) node.get("manaCost").numberValue();
        int rarityId = (Integer) node.get("rarityId").numberValue();
        String name = node.get("name").asText();
        String image = node.get("image").asText();
        return new WeaponCardPresentation(attack, durability, manaCost,
                rarityCardResolver.resolveRarityCard(rarityId), name, image);
    }
}
