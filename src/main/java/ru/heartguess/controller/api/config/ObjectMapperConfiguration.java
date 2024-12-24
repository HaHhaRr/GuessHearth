package ru.heartguess.controller.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.heartguess.controller.api.resolvers.ClassTypeResolver;
import ru.heartguess.controller.api.resolvers.DeserializerResolver;
import ru.heartguess.models.cards.presentation.hero.HeroCardDeserializer;
import ru.heartguess.models.cards.presentation.hero.HeroCardPresentation;
import ru.heartguess.models.cards.presentation.location.LocationCardDeserializer;
import ru.heartguess.models.cards.presentation.location.LocationCardPresentation;
import ru.heartguess.models.cards.presentation.minion.MinionCardDeserializer;
import ru.heartguess.models.cards.presentation.minion.MinionCardPresentation;
import ru.heartguess.models.cards.presentation.spell.SpellCardDeserializer;
import ru.heartguess.models.cards.presentation.spell.SpellCardPresentation;
import ru.heartguess.models.cards.presentation.weapon.WeaponCardDeserializer;
import ru.heartguess.models.cards.presentation.weapon.WeaponCardPresentation;

@Configuration
public class ObjectMapperConfiguration {

    @Autowired
    private HeroCardDeserializer heroCardDeserializer;

    @Autowired
    private LocationCardDeserializer locationCardDeserializer;

    @Autowired
    private MinionCardDeserializer minionCardDeserializer;

    @Autowired
    private SpellCardDeserializer spellCardDeserializer;

    @Autowired
    private WeaponCardDeserializer weaponCardDeserializer;

    @Bean
    public ObjectMapper cardPresentationMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(HeroCardPresentation.class, heroCardDeserializer);
        module.addDeserializer(LocationCardPresentation.class, locationCardDeserializer);
        module.addDeserializer(MinionCardPresentation.class, minionCardDeserializer);
        module.addDeserializer(SpellCardPresentation.class, spellCardDeserializer);
        module.addDeserializer(WeaponCardPresentation.class, weaponCardDeserializer);
        mapper.registerModule(module);

        return mapper;
    }
}
