package ru.heart.guess.heartguess.controller.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ObjectMapperWrapper {
    ObjectMapper objectMapper = new ObjectMapper();

    public ObjectMapperWrapper() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T readValue(String content, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(content, valueType);
    }
}
