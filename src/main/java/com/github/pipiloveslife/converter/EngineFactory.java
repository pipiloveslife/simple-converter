package com.github.pipiloveslife.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.pipiloveslife.converter.annotation.Deep;
import com.github.pipiloveslife.converter.enums.EnumManager.ConvertTiming;
import com.github.pipiloveslife.converter.enums.EnumManager.FieldType;
import com.github.pipiloveslife.converter.impl.ListEngine;
import com.github.pipiloveslife.converter.impl.ObjectEngine;
import com.github.pipiloveslife.converter.impl.base.EmptyEngine;
import com.github.pipiloveslife.converter.interfaces.ConvertCommand;
import com.github.pipiloveslife.converter.utils.SimpleUtils;
import org.springframework.context.ApplicationContext;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
public class EngineFactory {

    public static ConvertCommand convertOne(ApplicationContext context, Object object) {
        return convertOneAtTime(context, object, ConvertTiming.NORMAL);
    }

    public static ConvertCommand convertOneAtTime(ApplicationContext context, Object object, ConvertTiming timing) {
        SimpleUtils.assertNotNull(object);
        return ObjectEngine.instance(context, object, timing);
    }

    public static ConvertCommand convertList(ApplicationContext context, List<?> list) {
        return convertListAtTime(context, list, ConvertTiming.NORMAL);
    }

    public static ConvertCommand convertListAtTime(ApplicationContext context, List<?> list,
                                                   ConvertTiming timing) {
        SimpleUtils.assertNotNull(list);
        if (list.isEmpty()) {
            return EmptyEngine::new;
        } else {
            return ListEngine.instance(context, list, timing);
        }
    }

    public static ConvertCommand imNotSure(Object object, ApplicationContext context, ConvertTiming timing) {
        SimpleUtils.assertNotNull(object);
        if (object instanceof List) {
            return convertListAtTime(context, (List)object, timing);
        } else if (object instanceof Object[]) {
            return convertListAtTime(context, Arrays.asList((Object[])object), timing);
        } else {
            return convertOneAtTime(context, object, timing);
        }
    }

    public static ConvertCommand deepMultilineEngine(ApplicationContext context, List<?> list, Deep deep,
                                                     Field field, ConvertTiming timing) {
        FieldType type = deep.deepBy();
        switch (type) {
            case OBJECT:
                return deepConvertMultiLine(list, field, context, List::add, timing);
            case ARRAY:
                return deepConvertMultiLine(list, field, context,
                    (result, item) -> result.addAll(Arrays.asList((Object[])item)), timing);
            case LIST:
                return deepConvertMultiLine(list, field, context, (result, item) -> result.addAll((List)item), timing);
            default:
                return EmptyEngine::new;
        }
    }

    public static ConvertCommand deepObjectEngine(ApplicationContext context, Object object, Deep deep,
                                                  Field field, ConvertTiming timing) {
        FieldType type = deep.deepBy();
        Object fieldValue = SimpleUtils.safeGet(field, object);
        if (fieldValue == null) {
            return EmptyEngine::new;
        }
        switch (type) {
            case OBJECT:
                return convertOneAtTime(context, fieldValue, timing);
            case ARRAY:
                List<Object> objectArray = Arrays.asList((Object[])fieldValue);
                return convertListAtTime(context, objectArray, timing);
            case LIST:
                return convertListAtTime(context, (List)fieldValue, timing);
            default:
                return EmptyEngine::new;
        }
    }

    private static ConvertCommand deepConvertMultiLine(List<?> list, Field field,
                                                       ApplicationContext context,
                                                       BiConsumer<List<Object>, Object> howToJoinList,
                                                       ConvertTiming timing) {
        List<Object> multiValue = new ArrayList<>();
        field.setAccessible(true);
        for (Object item : list) {
            try {
                Object value = field.get(item);
                if (value != null) {
                    howToJoinList.accept(multiValue, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return convertListAtTime(context, multiValue, timing);
    }
}
