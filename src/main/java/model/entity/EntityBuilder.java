package model.entity;

import java.lang.reflect.InvocationTargetException;

public class EntityBuilder {

    public static Object buildNewEntity(Class entity, Object[] array) {
        try {
            return entity.getDeclaredConstructor(Object[].class).newInstance(array);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
