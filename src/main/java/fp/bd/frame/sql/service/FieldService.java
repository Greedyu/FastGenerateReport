package fp.bd.frame.sql.service;

import fp.bd.frame.sql.dao.FieldDao;
import fp.bd.frame.sql.dto.FieldQuery;
import fp.bd.frame.sql.po.FieldPo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fieldService")
public class FieldService {
    @Autowired
    private FieldDao fieldDao;

    public void addFieldList(List<FieldPo> fieldList){
        fieldDao.batchInsertOrUpdate(fieldList);
    }

    public List<FieldPo> queryFieldList(@RequestBody FieldQuery fieldQuery ){
        return fieldDao.queryFieldList(fieldQuery);
    }

    public List<String> querySameNameField(@Param("第一张表")String firstTableCode ,@Param("第二张表")String secondTableCode){
        if (StringUtils.isEmpty(firstTableCode)||StringUtils.isEmpty(secondTableCode)){
            return null;
        }
        return fieldDao.querySameNameField(firstTableCode,secondTableCode);
    }
}
