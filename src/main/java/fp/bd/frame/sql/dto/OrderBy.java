package fp.bd.frame.sql.dto;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;
import fp.bd.frame.sql.po.FieldPo;

@APIValueObject("排序")
public class OrderBy extends FieldPo {
    @APIField("是否倒序")
    private Integer descFlag = 0;

    public Integer getDescFlag() {
        return descFlag;
    }

    public OrderBy setDescFlag(Integer descFlag) {
        this.descFlag = descFlag;
        return this;
    }
}
