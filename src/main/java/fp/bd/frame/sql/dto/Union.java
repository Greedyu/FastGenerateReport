package fp.bd.frame.sql.dto;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;

@APIValueObject("纵向关联")
public class Union {

    @APIField("第一个表名")
    private String firstTableCode;

    @APIField("第二个表名")
    private String secondTableCode;

    @APIField("新建表名")
    private String newTableCode;

    public String getFirstTableCode() {
        return firstTableCode;
    }

    public Union setFirstTableCode(String firstTableCode) {
        this.firstTableCode = firstTableCode;
        return this;
    }

    public String getSecondTableCode() {
        return secondTableCode;
    }

    public Union setSecondTableCode(String secondTableCode) {
        this.secondTableCode = secondTableCode;
        return this;
    }

    public String getNewTableCode() {
        return newTableCode;
    }

    public Union setNewTableCode(String newTableCode) {
        this.newTableCode = newTableCode;
        return this;
    }
}
