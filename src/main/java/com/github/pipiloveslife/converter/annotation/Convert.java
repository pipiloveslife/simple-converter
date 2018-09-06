package com.github.pipiloveslife.converter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.pipiloveslife.converter.enums.EnumManager.ConvertTiming;
import com.github.pipiloveslife.converter.enums.EnumManager.SourceFrom;
import com.github.pipiloveslife.converter.impl.base.DefaultConverter;
import com.github.pipiloveslife.converter.interfaces.Converter;

import static com.github.pipiloveslife.converter.constant.Constants.EMPTY;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Convert {
    String name() default EMPTY;

    Class<? extends Converter> using() default DefaultConverter.class;

    String[] arguments() default EMPTY;

    SourceFrom from() default SourceFrom.NAMED_FIELD;

    String field() default EMPTY;

    boolean useCache() default true;

    boolean allowNull() default false;

    ConvertTiming[] when() default ConvertTiming.NORMAL;
}
