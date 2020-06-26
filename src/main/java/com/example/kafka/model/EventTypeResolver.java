package com.example.kafka.model;

import java.util.Set;
import org.reflections.Reflections;
import com.example.kafka.exception.EventMappingException;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class EventTypeResolver implements TypeIdResolver {

    private static final String MODEL_PACKAGE = KafkaMessage.class.getPackage().getName();

    private JavaType baseType;
    private Set<Class<?>> models;

    @Override
    public void init(JavaType baseType) {
        this.baseType = baseType;

        Reflections reflections = new Reflections(MODEL_PACKAGE);
        models = reflections.getTypesAnnotatedWith(EventType.class);
    }

    @Override
    public String idFromValue(Object o) {
        return idFromValueAndType(o, o.getClass());
    }

    @Override
    public String idFromValueAndType(Object o, Class<?> clazz) {
        return ((KafkaMessage<?>) o).getEventType();
    }

    @Override
    public String idFromBaseType() {
        return null;
    }

    @Override
    public JavaType typeFromId(DatabindContext databindContext, String eventType) {
        Class<?> clazz = models.stream().filter(model -> model.getAnnotation(EventType.class).value().equals(eventType))
                .findAny()
                .orElseThrow(() -> new EventMappingException(eventType));

        return TypeFactory.defaultInstance().constructSpecializedType(baseType, clazz);
    }

    @Override
    public String getDescForKnownTypeIds() {
        return null;
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.MINIMAL_CLASS;
    }
}
