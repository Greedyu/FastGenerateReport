package fp.bd.frame.sql.dao;

import fp.bd.frame.sql.dto.TableQuery;
import fp.bd.frame.sql.po.TablePo;

import java.util.List;

public interface TableDao {
    void add(TablePo tablePo);

    List<TablePo> queryTableList(TableQuery tableQuery);

    String queryCreateSql(String tableCode);
}
