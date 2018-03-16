package fp.bd.frame.sql.service;

import com.fp.spring.expand.exception.SOAExceptionAssert;
import com.fp.spring.expand.exception.SOAExceptionCode;
import fp.bd.frame.sql.dao.DeployTableDao;
import fp.bd.frame.sql.dto.DeployTableQuery;
import fp.bd.frame.sql.po.DeployTablePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/deployTableService")
@RestController
public class DeployTableService {

    @Autowired
    DeployTableDao deployTableDao;

    public void createDeployList(List<DeployTablePo> deployTableList){
        deployTableDao.batchInsert(deployTableList);
    }

    public List<String> queryDeployTableCodeList(@RequestBody DeployTableQuery deployTableQuery){
        return deployTableDao.queryDeployTableCodeList(deployTableQuery);
    }

    public void deleteDeploy(String tableCode) {
        SOAExceptionAssert.isNotBlank(tableCode, SOAExceptionCode.PARAM_REQUIRED_EXCEPTION, "表不能为空");
        deployTableDao.deleteDeploy(tableCode);
    }
}
