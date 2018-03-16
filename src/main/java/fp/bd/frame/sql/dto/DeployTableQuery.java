package fp.bd.frame.sql.dto;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;

@APIValueObject("依赖表查询")
public class DeployTableQuery {
    @APIField("主表")
    private String tableCode;

    public String getTableCode() {
        return tableCode;
    }

    public DeployTableQuery setTableCode(String tableCode) {
        this.tableCode = tableCode;
        return this;
    }
}
