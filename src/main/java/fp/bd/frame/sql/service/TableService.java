package fp.bd.frame.sql.service;

import com.fp.spring.expand.exception.SOAExceptionAssert;
import com.fp.spring.expand.exception.SOAExceptionCode;
import fp.bd.frame.sql.dao.TableDao;
import fp.bd.frame.sql.dto.TableQuery;
import fp.bd.frame.sql.po.TablePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/tableService")
@RestController
public class TableService {

    @Autowired
    TableDao tableDao;

    @Autowired
    FieldService fieldService;

    Logger logger = LoggerFactory.getLogger(TableService.class);


    public void createTable(TablePo tablePo){
        tableDao.add(tablePo);
    }

    public List<TablePo> queryTableList(@RequestBody TableQuery tableQuery){
        return tableDao.queryTableList(tableQuery);
    }

    public String queryCreateSql(String tableCode){
        SOAExceptionAssert.isNotBlank(tableCode, SOAExceptionCode.PARAM_REQUIRED_EXCEPTION, "表不能为空");
        return tableDao.queryCreateSql(tableCode);
    }
}
