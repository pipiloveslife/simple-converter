package com.github.pipiloveslife.converter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.pipiloveslife.converter.enums.EnumManager.FieldType;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Deep {

    FieldType deepBy() default FieldType.LIST;
}
