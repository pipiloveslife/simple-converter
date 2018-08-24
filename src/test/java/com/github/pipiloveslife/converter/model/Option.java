package com.github.pipiloveslife.converter.model;

import com.github.pipiloveslife.converter.annotation.Convert;
import com.github.pipiloveslife.converter.impl.TypeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author by pipiloveslife on 2018/8/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Option {

    private String title;
    private Integer code;
    @Convert(using = TypeConverter.class, arguments = "2", field = "code")
    private String value;
    private boolean check;
}
