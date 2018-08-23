package com.github.pipiloveslife.converter.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.pipiloveslife.converter.enums.EnumManager.SourceFrom;
import org.springframework.beans.BeanWrapper;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
public class SimpleUtils {

    public static List<Field> getDeclaredFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> result = new ArrayList<>(Arrays.asList(fields));
        Class<?> superclass = clazz.getSuperclass();
        while (superclass != null) {
            result.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }
        return result;
    }

    public static Object safeGet(Field field, Object target) {
        field.setAccessible(true);
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> boolean contains(T[] array, T object) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (T item : array) {
            if (item.equals(object)) {
                return true;
            }
        }
        return false;
    }

    public static void setWritable(BeanWrapper wrapper, String name, Object value) {
        if (wrapper.isWritableProperty(name)) {
            wrapper.setPropertyValue(name, value);
        }
    }

    public static Object getReadable(BeanWrapper wrapper, String name) {
        if (wrapper.isReadableProperty(name)) {
            return wrapper.getPropertyValue(name);
        } else {
            return null;
        }
    }

    public static Object getSourceValue(BeanWrapper wrapper, SourceFrom sourceFrom, String currentName,
                                        String fieldName) {
        switch (sourceFrom) {
            case CURRENT_FIELD:
                return SimpleUtils.getReadable(wrapper, currentName);
            case NAMED_FIELD:
                return SimpleUtils.getReadable(wrapper, fieldName);
            case BEAN_WRAPPER:
                return wrapper;
            default:
                return null;
        }
    }

    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new RuntimeException("null object cannot be convert");
        }
    }

    public static <T> void safeAdd(List<T> list, T target) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (target != null) {
            list.add(target);
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
