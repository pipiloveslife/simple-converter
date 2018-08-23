package com.github.pipiloveslife.converter.enums;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
public interface EnumManager {
    enum SourceFrom {
        /**
         * 原始值来自当前注解字段
         */
        CURRENT_FIELD,
        /**
         * 原始值来自指定字段
         */
        NAMED_FIELD,
        /**
         * 将一个BeanWrapper包装后的对象传递给转换器
         */
        BEAN_WRAPPER;
    }

    enum ConvertTiming {
        /**
         * 默认值
         */
        NORMAL,
        /**
         * 情况1
         */
        ONCE,
        /**
         * 情况2
         */
        SOMETIMES,
        /**
         * 情况3
         */
        ALWAYS,
        /**
         * 情况4
         */
        THIS_TIME,
        /**
         * 情况5
         */
        NOT_NOW;
    }

    enum FieldType {
        /**
         * 对象类型
         */
        OBJECT,
        /**
         * 数组类型
         */
        ARRAY,
        /**
         * 集合类型
         */
        LIST;
    }
}
