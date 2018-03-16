package fp.bd.frame.sql.po;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;

@APIValueObject("表的依赖")
public class DeployTablePo {

    @APIField("主表")
    private String tableCode;

    @APIField("依赖表")
    private String deployTableCode;

    public String getTableCode() {
        return tableCode;
    }

    public DeployTablePo setTableCode(String tableCode) {
        this.tableCode = tableCode;
        return this;
    }

    public String getDeployTableCode() {
        return deployTableCode;
    }

    public DeployTablePo setDeployTableCode(String deployTableCode) {
        this.deployTableCode = deployTableCode;
        return this;
    }

}
