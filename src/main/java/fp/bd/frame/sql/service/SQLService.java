package fp.bd.frame.sql.service;

import com.fp.spring.expand.exception.SOAExceptionAssert;
import com.fp.spring.expand.exception.SOAExceptionCode;
import fp.bd.frame.sql.dto.*;
import fp.bd.frame.sql.po.DeployTablePo;
import fp.bd.frame.sql.po.FieldPo;
import fp.bd.frame.sql.po.TablePo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/sqlService")
@RestController
public class SQLService {

    @Resource(name = "prestoSqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    TableService tableService;

    @Autowired
    FieldService fieldService;

    @Autowired
    DeployTableService deployTableService;

    Logger logger = LoggerFactory.getLogger(SQLService.class);

    /**
     * key : 表名
     * value : 别名
     */
    static ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();

    @RequestMapping("/runSql")
    public void runSQL(Query query) throws Exception {
        String sql = resolveSQL(query);
        executeSQL(sql);
    }

    /**
     * 纵向关联表
     * @param union
     * @throws Exception
     */
    @RequestMapping("/unionTable")
    public void unionTable(@RequestBody Union union)throws Exception{
        SOAExceptionAssert.isNotBlank(union.getFirstTableCode(), SOAExceptionCode.PARAM_REQUIRED_EXCEPTION, "缺少第一个表");
        SOAExceptionAssert.isNotBlank(union.getSecondTableCode(), SOAExceptionCode.PARAM_REQUIRED_EXCEPTION, "缺少第二个表");
        SOAExceptionAssert.isNotBlank(union.getNewTableCode(), SOAExceptionCode.PARAM_REQUIRED_EXCEPTION, "缺少新表");
        String sql = createOrUpdateView(union.getNewTableCode());
        sql += " select * from " + union.getFirstTableCode() + " union select * from " + union.getSecondTableCode();
        executeSQL(sql);
    }

    /**
     * 获取报表时重新加载递归一遍原数据
     * @param tableCode
     * @throws Exception
     */
    @RequestMapping("/reloadTable")
    public void reloadTable(String tableCode)throws Exception{
        List<String> deployTableCodeList = deployTableService.queryDeployTableCodeList(new DeployTableQuery().setTableCode(tableCode));
        if (CollectionUtils.isEmpty(deployTableCodeList) ){
            return;
        }
        for (String deployTableCode : deployTableCodeList){
            reloadTable(deployTableCode);
            String createSql = tableService.queryCreateSql(deployTableCode);
            if (!StringUtils.isEmpty(createSql)){
                executeSQL(createSql);
            }
        }
    }

    public String resolveSQL(Query query) throws Exception {
        createTableAlias(query.getTableCodeList());
        StringBuffer sb = new StringBuffer();
        sb.append(transformFieldList(query.getFieldList()));
        sb.append(transformJoinList(query.getJoinList()));
        sb.append(transformWhereFilterList(query.getWhereFilterList()));
        sb.append(transformGroupByList(query.getGroupByList()));
        sb.append(transformSortList(query.getOrderByList()));
        sb.insert(0, createOrUpdateView(query.getNewTableCode()));
        System.out.println(sb.toString());
        createTable(query, sb.toString());
        createFieldList(query);
        createDeployItem(query);
        return sb.toString();
    }

    public void executeSQL(String sql) throws Exception {
        System.out.println(sql);
        sqlSessionFactory.openSession().getConnection().createStatement().execute(sql);
    }


    private String createOrUpdateView(String tableCode) {
        String sql = "";
        if (!StringUtils.isEmpty(tableCode)) {
            sql += "CREATE OR REPLACE view " + tableCode + " as ";
        }
        return sql;
    }


    private String transformGroupByList(List<GroupBy> groupByList) {
        String sql = "";
        if (!CollectionUtils.isEmpty(groupByList)) {
            sql += " group by ";
            for (GroupBy groupBy : groupByList) {
                sql += threadLocal.get().get(groupBy.getTableCode()) + "." + groupBy.getFieldCode() + " ,";
            }
            sql = sql.substring(0, sql.length() - 1);
        }
        return sql;
    }

    private void createTableAlias(List<String> tableCodeList) {
        threadLocal.set(new HashMap<>());
        char alias = 'a';
        for (int i = 0; i < tableCodeList.size(); i++) {
            threadLocal.get().put(tableCodeList.get(i), String.valueOf(alias));
            alias += 1;
        }
    }


    private String transformFieldList(List<FieldPo> fieldList) {
        if (CollectionUtils.isEmpty(fieldList)) {
            logger.error("不存在查询项");
            return null;
        }
        String sql = "select ";
        for (FieldPo field : fieldList) {
            if (field.getArithmeticFlag() != 0){
                sql += " COALESCE(TRY(CAST(1.00 * " + field.getFieldCode() + " AS DECIMAL(38, 3))), 0) ";
            }else {
                sql += field.getFieldCode();
            }
            if (!StringUtils.isEmpty(field.getFieldName())) {
                sql += " as \"" + field.getFieldName() + "\"";
            }
            sql += ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        for (Map.Entry<String, String> entry : threadLocal.get().entrySet()) {
            sql = sql.replaceAll(entry.getKey(), entry.getValue());
        }
        return sql;
    }

    private String transformJoinList(List<Join> joinList) throws Exception {
        String sql = "";
        if (!CollectionUtils.isEmpty(joinList)) {
            for (Join joinpo : joinList) {
                if (!StringUtils.isEmpty(joinpo.getMainTable())) {
                    sql = " from " + joinpo.getMainTable() + ' ' + threadLocal.get().get(joinpo.getMainTable()) + ' ' + sql;
                } else if (!StringUtils.isEmpty(joinpo.getJoinTable())) {
                    sql += joinpo.getJoinType() + " " + joinpo.getJoinTable() + " " + threadLocal.get().get(joinpo.getJoinTable()) + " ";
                    if (!CollectionUtils.isEmpty(joinpo.getJoinTableList())) {
                        sql += " on ";
                        for (Join.JoinTable joinTable : joinpo.getJoinTableList()) {
                            sql += threadLocal.get().get(joinTable.getFirstTableCode()) + "." + joinTable.getFirstFieldCode() + " = " +
                                    threadLocal.get().get(joinTable.getSecondTableCode()) + "." + joinTable.getSecondFieldCode() + " and ";
                        }
                        sql = sql.substring(0, sql.length() - 4);
                    }
                } else {
                    logger.error("查询表不能为null");
                }
            }
        } else {
            throw new Exception("缺少主表");
        }
        return sql;
    }

    private String transformWhereFilterList(List<WhereFilter> whereFilterList) {
        String sql = "";
        if (!CollectionUtils.isEmpty(whereFilterList)) {
            sql += " where ";
        }
        return sql + transformChildFilter(whereFilterList);
    }

    private String transformChildFilter(List<WhereFilter> whereFilterList) {
        String sql = " ";
        if (!CollectionUtils.isEmpty(whereFilterList)) {
            for (WhereFilter whereFilter : whereFilterList) {
                if (!whereFilter.getMultipleFlag()) {
                    sql += " " + transformFilterByWay(whereFilter) + whereFilter.getRelation();
                } else {
                    sql += " (";
                    sql += transformChildFilter(whereFilter.getWhereFilterList());
                    sql += ") " + whereFilter.getRelation();
                }
            }
            if (sql.toLowerCase().endsWith("and")) {
                sql = sql.substring(0, sql.length() - 3);
            }
            if (sql.toLowerCase().endsWith("or")) {
                sql = sql.substring(0, sql.length() - 2);
            }
        }
        return sql;
    }

    private String transformFilterByWay(WhereFilter whereFilter) {
        String sql = "";
        if (whereFilter.getArithmeticFlag() == 0) {
            sql = threadLocal.get().get(whereFilter.getTableCode()) + "." + whereFilter.getFieldCode() + whereFilter.getFilterWay() + " '" + whereFilter.getFirstCondition() + "' ";
        } else {
            sql = threadLocal.get().get(whereFilter.getTableCode()) + "." + whereFilter.getFieldCode() + whereFilter.getFilterWay() + whereFilter.getFirstCondition() + " ";
        }
        return sql;
    }


    private String transformSortList(List<OrderBy> orderByList) {
        String sql = "";
        if (CollectionUtils.isEmpty(orderByList)){
            return sql;
        }
        sql += " order by ";
        for (OrderBy orderBy : orderByList){
            if (!StringUtils.isEmpty(orderBy.getTableCode())){
                sql += orderBy.getTableCode() + ".";
            }
            sql += orderBy.getFieldCode();
            if (orderBy.getDescFlag() != 0){
                sql += " desc";
            }
            sql += ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        return sql;
    }

    private void createTable(Query query, String sql) {
        if (!StringUtils.isEmpty(query.getNewTableCode())) {
            TablePo tablePo = new TablePo();
            tablePo.setCreateSql(sql);
            tablePo.setTableCode(query.getNewTableCode());
            tablePo.setTableName(query.getNewTableName());
            tableService.createTable(tablePo);
        }
    }

    private void createFieldList(Query query) throws Exception{
        if (!CollectionUtils.isEmpty(query.getFieldList())) {
            if (!StringUtils.isEmpty(query.getNewTableCode())) {
                List<FieldPo> fieldList = new ArrayList<>();
                for (FieldPo fieldPo : query.getFieldList()) {
                    fieldPo.setFieldCode(fieldPo.getFieldName());
                    fieldPo.setTableCode(query.getNewTableCode());
                    fieldPo.setTableName(query.getNewTableName());
                    fieldList.add(fieldPo);
                }
                fieldService.addFieldList(fieldList);
                createSchema(query.getNewTableCode());
            }
        }
    }

    private void createSchema(String newTableCode) throws Exception{
        String[] str = newTableCode.split("\\.");
        if (str.length > 1){
            sqlSessionFactory.openSession().getConnection().createStatement().execute("CREATE SCHEMA IF NOT EXISTS " + str[0]);
        }
    }

    private void createDeployItem(Query query) {
        if (CollectionUtils.isEmpty(query.getJoinList()) || StringUtils.isEmpty(query.getNewTableCode())){
            return;
        }
        deployTableService.deleteDeploy(query.getTableCode());
        Set<String> deployTableCodeList = query.getJoinList().stream().filter(t-> t.getJoinTable() != null).map(t->t.getJoinTable()).collect(Collectors.toSet());
        deployTableCodeList.add(query.getTableCode());
        List<DeployTablePo> deployTableList = new ArrayList<>();
        for (String deployTableCode : deployTableCodeList){
            DeployTablePo deployTablePo = new DeployTablePo().setDeployTableCode(deployTableCode).setTableCode(query.getNewTableCode());
            deployTableList.add(deployTablePo);
        }
        deployTableService.createDeployList(deployTableList);
    }

}
