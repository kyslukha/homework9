package ua.com.alevel.util;

import lombok.experimental.UtilityClass;

import ua.com.alevel.entity.AbstractEntity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Iehor Funtusov, created 28/12/2020 - 12:46 PM
 */

@UtilityClass
public class ReflectionUtil {

    public Set<Field> getAllFieldsByClass(Class<? extends AbstractEntity> aClass) {
        Set<Field> fields = new HashSet<>();
        initFields(aClass, fields);
        return fields;
    }

    private void initFields(Class<?> parentClass, Set<Field> fields) {
        fields.addAll(Arrays.asList(parentClass.getDeclaredFields()));
        if (parentClass.getSuperclass() != null) {
            initFields(parentClass.getSuperclass(), fields);
        }
    }
}