package fp.bd.frame.sql.po;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;

@APIValueObject("字段")
public class FieldPo {
    @APIField("表中文名称")
    private String tableName;

    @APIField("表")
    private String tableCode;

    @APIField("字段中文名")
    private String fieldName;

    @APIField("字段")
    private String fieldCode;

    @APIField("算数标志")
    private Integer arithmeticFlag = 0;

    public String getTableName() {
        return tableName;
    }

    public FieldPo setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableCode() {
        return tableCode;
    }

    public FieldPo setTableCode(String tableCode) {
        this.tableCode = tableCode;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public FieldPo setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public FieldPo setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
        return this;
    }

    public FieldPo(String tableName, String tableCode, String fieldName, String fieldCode) {
        this.tableName = tableName;
        this.tableCode = tableCode;
        this.fieldName = fieldName;
        this.fieldCode = fieldCode;
    }

    public FieldPo() {
    }

    public Integer getArithmeticFlag() {
        return arithmeticFlag;
    }

    public FieldPo setArithmeticFlag(Integer arithmeticFlag) {
        this.arithmeticFlag = arithmeticFlag;
        return this;
    }
}
