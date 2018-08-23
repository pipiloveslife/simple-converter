package com.github.pipiloveslife.converter.local;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
public class LocalStorage {

    private static ThreadLocal<Map<Field, Map<String, Object>>> converterStorage;
    private static ThreadLocal<LinkedList<String>> clearListenerStack;

    static {
        converterStorage = ThreadLocal.withInitial(() -> new HashMap<>(30));
        clearListenerStack = ThreadLocal.withInitial(LinkedList::new);
    }

    public static Map<String, Object> getConvertCacheMap(Field field, int size) {
        return converterStorage.get().computeIfAbsent(field, f -> new HashMap<>(size));
    }

    public static void setListener() {
        clearListenerStack.get().add(UUID.randomUUID().toString());
    }

    public static void clearListener() {
        LinkedList<String> stack = clearListenerStack.get();
        stack.removeLast();
        if (stack.size() == 0) {
            clearCache();
        }
    }

    public static void clearCache() {
        converterStorage.remove();
        clearListenerStack.remove();
    }
}
