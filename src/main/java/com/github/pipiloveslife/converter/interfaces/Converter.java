package com.github.pipiloveslife.converter.interfaces;

import com.github.pipiloveslife.converter.annotation.Convert;

/**
 * <p>
 * core interface, a converter of field must implement this interface
 * </p>
 * <p>
 * if you need spring injection, add @Component on the implement class and call the factory method with
 * ApplicationContext parameter
 * </p>
 *
 * @author by pipiloveslife on 2018/8/23.
 */
public interface Converter {

    /**
     * converter core method
     *
     * @param source parameter to converter
     * @param config config to converter
     * @return convert result
     */
    Object convert(Object source, Convert config);

    /**
     * generate a cache key, you can override this method
     *
     * @param source parameter to converter
     * @return unique key
     */
    default String generateKey(Object source) {
        return source.toString();
    }
}
