package com.github.pipiloveslife.converter.model;

import java.util.List;

import com.github.pipiloveslife.converter.annotation.Convert;
import com.github.pipiloveslife.converter.annotation.Deep;
import com.github.pipiloveslife.converter.enums.EnumManager.ConvertTiming;
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
public class Paper {

    private String title;

    private Integer type;
    @Convert(using = TypeConverter.class, arguments = "0", field = "type", when = ConvertTiming.SOMETIMES)
    private String typeValue;

    @Deep
    private List<Question> questions;
}
