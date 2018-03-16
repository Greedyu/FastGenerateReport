package fp.bd.frame.sql.dao;

import fp.bd.frame.sql.dto.DeployTableQuery;
import fp.bd.frame.sql.po.DeployTablePo;

import java.util.List;

public interface DeployTableDao {

    void batchInsert(List<DeployTablePo> list);

    List<String> queryDeployTableCodeList(DeployTableQuery deployTableQuery);

    void deleteDeploy(String tableCode);
}
