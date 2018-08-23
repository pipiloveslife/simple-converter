package com.github.pipiloveslife.converter.interfaces;

import com.github.pipiloveslife.converter.annotation.Convert;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
public interface Converter {

    /**
     * 转换器核心方法
     *
     * @param source 提供给转换器的参数
     * @param config 提供给转换器的配置(即注解)
     * @return 转换完成的值
     */
    Object convert(Object source, Convert config);

    /**
     * 用于缓存时的key值生成
     *
     * @param source 提供给key生成器的参数
     * @return 生成的唯一性标识key
     */
    default String generateKey(Object source) {
        return source.toString();
    }
}
