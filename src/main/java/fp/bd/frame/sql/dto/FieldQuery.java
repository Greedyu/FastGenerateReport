package fp.bd.frame.sql.dto;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;

@APIValueObject
public class FieldQuery {
    @APIField("字段名称模糊查询")
    private String fieldNameLike;

    public String getFieldNameLike() {
        return fieldNameLike;
    }

    public FieldQuery setFieldNameLike(String fieldNameLike) {
        this.fieldNameLike = fieldNameLike;
        return this;
    }
}
