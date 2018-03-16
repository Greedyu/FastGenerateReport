package fp.bd.frame.sql.dto;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;

import java.util.List;

@APIValueObject("表查询")
public class TableQuery {
    @APIField("表名称模糊查询")
    private String tableNameLike;

    @APIField("字段名称模糊查询")
    private String fieldNameLike;

    @APIField("表列表")
    private List<String> tableCodeList;

    @APIField("表")
    private String tableCode;

    public String getTableNameLike() {
        return tableNameLike;
    }

    public TableQuery setTableNameLike(String tableNameLike) {
        this.tableNameLike = tableNameLike;
        return this;
    }

    public String getFieldNameLike() {
        return fieldNameLike;
    }

    public TableQuery setFieldNameLike(String fieldNameLike) {
        this.fieldNameLike = fieldNameLike;
        return this;
    }

    public List<String> getTableCodeList() {
        return tableCodeList;
    }

    public TableQuery setTableCodeList(List<String> tableCodeList) {
        this.tableCodeList = tableCodeList;
        return this;
    }

    public String getTableCode() {
        return tableCode;
    }

    public TableQuery setTableCode(String tableCode) {
        this.tableCode = tableCode;
        return this;
    }
}

