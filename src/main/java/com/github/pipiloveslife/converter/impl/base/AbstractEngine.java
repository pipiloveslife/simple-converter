package com.github.pipiloveslife.converter.impl.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.github.pipiloveslife.converter.EngineFactory;
import com.github.pipiloveslife.converter.annotation.Convert;
import com.github.pipiloveslife.converter.annotation.Deep;
import com.github.pipiloveslife.converter.enums.EnumManager.ConvertTiming;
import com.github.pipiloveslife.converter.impl.ConvertExecutor;
import com.github.pipiloveslife.converter.interfaces.ConvertCommand;
import com.github.pipiloveslife.converter.interfaces.FieldCommand;
import com.github.pipiloveslife.converter.utils.SimpleUtils;
import org.springframework.context.ApplicationContext;

/**
 * @author by 高垠琪 on 2018/8/23.
 */
public abstract class AbstractEngine implements ConvertCommand {
    protected List<FieldCommand> fieldExecutors;
    protected List<ConvertCommand> deepEngines;

    protected ConvertCommand init(ApplicationContext context, Class<?> clazz, Object object, List<?> list,
                                  int size, ConvertTiming timing) {
        List<Field> declaredFields = SimpleUtils.getDeclaredFields(clazz);
        this.deepEngines = new ArrayList<>();
        this.fieldExecutors = new ArrayList<>(declaredFields.size());
        for (Field field : declaredFields) {
            Convert config = field.getAnnotation(Convert.class);
            Deep deep = field.getAnnotation(Deep.class);
            this.fieldExecutors.add(ConvertExecutor.instance(context, config, deep, field, size, timing));
            if (deep != null && config == null) {
                this.deepEngines.add(
                    object != null ? EngineFactory.deepObjectEngine(context, object, deep, field, timing)
                        : EngineFactory.deepMultilineEngine(context, list, deep, field, timing));
            }
        }
        return this;
    }
}
