package com.github.pipiloveslife.converter.impl;

import java.lang.reflect.Field;
import java.util.Map;

import com.github.pipiloveslife.converter.EngineFactory;
import com.github.pipiloveslife.converter.annotation.Convert;
import com.github.pipiloveslife.converter.annotation.Deep;
import com.github.pipiloveslife.converter.enums.EnumManager.ConvertTiming;
import com.github.pipiloveslife.converter.impl.base.EmptyExecutor;
import com.github.pipiloveslife.converter.interfaces.Converter;
import com.github.pipiloveslife.converter.interfaces.FieldCommand;
import com.github.pipiloveslife.converter.local.LocalStorage;
import com.github.pipiloveslife.converter.utils.SimpleUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.ApplicationContext;

import static com.github.pipiloveslife.converter.constant.Constants.EMPTY;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
@Data
@Accessors(chain = true)
public class ConvertExecutor implements FieldCommand {
    private Field currentField;
    private Convert config;
    private boolean single;
    private Map<String, Object> cacheMap;
    private ApplicationContext context;
    private ConvertTiming lazyJobTiming;

    private ConvertExecutor(Field field, Convert config, int size, ApplicationContext context) {
        this.currentField = field;
        this.config = config;
        this.single = !config.useCache() || size == 1;
        if (!this.single) {
            this.cacheMap = LocalStorage.getConvertCacheMap(field, size);
        }
        this.context = context;
    }

    public static FieldCommand instance(ApplicationContext context, Convert config, Deep deep, Field field, int size,
                                 ConvertTiming timing) {
        boolean needConvert = config != null && (SimpleUtils.contains(config.when(), timing) || SimpleUtils
            .contains(config.when(), ConvertTiming.NORMAL));
        ConvertExecutor executor = null;
        if (needConvert) {
            executor = new ConvertExecutor(field, config, size, context);
            if (deep != null) {
                executor.lazyJobTiming = timing;
            }
        }
        return executor == null ? EmptyExecutor::new : executor;
    }

    @Override
    public void execute(Object object) {
        Converter converter = this.getConverter();
        BeanWrapper wrapper = new BeanWrapperImpl(object);
        Object source = SimpleUtils.getSourceValue(wrapper, this.config.from(), this.currentField.getName(),
            this.config.field());
        Object value = this.convert(source, converter);
        if (value != null && this.lazyJobTiming != null) {
            EngineFactory.imNotSure(value, this.context, this.lazyJobTiming).execute();
        }
        SimpleUtils.setWritable(wrapper, this.currentField.getName(), value);
    }

    private Object convert(Object source, Converter converter) {
        if (source == null) {
            return null;
        }
        if (this.single) {
            return converter.convert(source, this.config);
        } else {
            String key = converter.generateKey(source);
            return this.cacheMap.computeIfAbsent(key, k -> converter.convert(source, this.config));
        }
    }

    private Converter getConverter() {
        String name = this.config.name();
        if (EMPTY.equals(name)) {
            return SimpleUtils.newInstance(this.config.using());
        } else {
            return this.context.getBean(name, Converter.class);
        }
    }
}
