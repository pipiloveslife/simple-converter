package com.github.pipiloveslife.converter.impl;

import com.github.pipiloveslife.converter.DataHelper;
import com.github.pipiloveslife.converter.annotation.Convert;
import com.github.pipiloveslife.converter.interfaces.Converter;

/**
 * @author by pipiloveslife on 2018/8/24.
 */
public class TypeConverter implements Converter {

    @Override
    public Object convert(Object source, Convert config) {
        return DataHelper.ARRAY[Integer.valueOf(config.arguments()[0])][Integer.valueOf(source.toString())];
    }
}
