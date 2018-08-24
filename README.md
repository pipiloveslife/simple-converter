# simple-converter
基于spring的查询转换器(将一些编码值通过注解方式转换为字符值/给某些字段赋值)

### 应用场景

查询场景下，结果中经常会有一些业务字段需要依赖其他服务或者无法直接取到值。为了减少代码中大量重复的字段赋值，查询。开发这一套转换工具用于字段值转换。依赖注解，减少业务代码中大量的赋值操作。

### 示例

```$xslt
/**
 * 数据实体类
 */
public class Paper {

    private String title;

    private Integer type;
    
    @Convert(using = TypeConverter.class, arguments = "0", field = "type", when = ConvertTiming.SOMETIMES)
    private String typeValue;
    
}
/**
 * 转换器
 */
public class TypeConverter implements Converter {

    @Override
    public Object convert(Object source, Convert config) {
        return DataHelper.ARRAY[Integer.valueOf(config.arguments()[0])][Integer.valueOf(source.toString())];
    }
}
/**
 * 业务代码
 */
public List<Paper> searchPaperList() {
    ……
    // 查询中的业务代码只有一行
    CommandFactory.convertList(null, paperList).execute();
    ……
}
```
参考：`test` 包下的 `com.github.pipiloveslife.converter.ConvertTest`