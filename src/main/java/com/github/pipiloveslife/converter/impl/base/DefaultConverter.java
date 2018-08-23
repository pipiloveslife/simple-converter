package com.github.pipiloveslife.converter.impl.base;

import com.github.pipiloveslife.converter.annotation.Convert;
import com.github.pipiloveslife.converter.interfaces.Converter;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
public class DefaultConverter implements Converter {
    @Override
    public Object convert(Object source, Convert config) {
        return source.toString();
    }
}
