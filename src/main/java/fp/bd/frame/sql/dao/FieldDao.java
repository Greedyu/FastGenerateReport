package fp.bd.frame.sql.dao;

import fp.bd.frame.sql.dto.FieldQuery;
import fp.bd.frame.sql.po.FieldPo;

import java.util.List;

public interface FieldDao {
    void batchInsertOrUpdate(List<FieldPo> list);

    List<FieldPo> queryFieldList(FieldQuery fieldQuery);

    List<String> querySameNameField(String firstTableCode, String secondTableCode);
}
