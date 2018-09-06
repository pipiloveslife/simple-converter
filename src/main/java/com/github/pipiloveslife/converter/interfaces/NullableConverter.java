package com.github.pipiloveslife.converter.interfaces;

/**
 * @author by 高垠琪 on 2018/9/6.
 */
public interface NullableConverter extends Converter {

    @Override
    String generateKey(Object source);
}
