package fp.bd.frame.sql.po;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;

@APIValueObject("表")
public class TablePo {
    @APIField("表中文名称")
    private String tableName;

    @APIField("表")
    private String tableCode;

    @APIField("建表sql语句")
    private String createSql;

    public String getTableName() {
        return tableName;
    }

    public TablePo setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableCode() {
        return tableCode;
    }

    public TablePo setTableCode(String tableCode) {
        this.tableCode = tableCode;
        return this;
    }

    public String getCreateSql() {
        return createSql;
    }

    public TablePo setCreateSql(String createSql) {
        this.createSql = createSql;
        return this;
    }
}
