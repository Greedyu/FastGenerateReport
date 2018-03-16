package fp.bd.frame.sql.dto;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;
import fp.bd.frame.sql.po.FieldPo;

import java.util.List;

@APIValueObject("查询实体")
public class Query {

    @APIField("表的中文名称")
    private String tableName;

    @APIField("主表")
    private String tableCode;

    @APIField("涉及表")
    private List<String> tableCodeList;

    @APIField("查询字段列表")
    private List<FieldPo> fieldList;

    @APIField("维度列表")
    private List<GroupBy> groupByList;

    @APIField("排序列表")
    private List<OrderBy> orderByList;

    @APIField("关联关系列表")
    private List<Join> joinList;

    @APIField("条件列表")
    private List<WhereFilter> whereFilterList;

    @APIField("生成表或视图的中文名称")
    private String newTableName;

    @APIField("生成表或视图")
    private String newTableCode;

    @APIField("创建表or视图")
    private Integer createType = 1;

    public String getTableName() {
        return tableName;
    }

    public Query setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableCode() {
        return tableCode;
    }

    public Query setTableCode(String tableCode) {
        this.tableCode = tableCode;
        return this;
    }

    public List<FieldPo> getFieldList() {
        return fieldList;
    }

    public Query setFieldList(List<FieldPo> fieldList) {
        this.fieldList = fieldList;
        return this;
    }

    public Query setOrderByList(List<OrderBy> orderByList) {
        this.orderByList = orderByList;
        return this;
    }

    public List<GroupBy> getGroupByList() {
        return groupByList;
    }

    public Query setGroupByList(List<GroupBy> groupByList) {
        this.groupByList = groupByList;
        return this;
    }

    public List<Join> getJoinList() {
        return joinList;
    }

    public Query setJoinList(List<Join> joinList) {
        this.joinList = joinList;
        return this;
    }

    public List<WhereFilter> getWhereFilterList() {
        return whereFilterList;
    }

    public Query setWhereFilterList(List<WhereFilter> whereFilterList) {
        this.whereFilterList = whereFilterList;
        return this;
    }

    public List<String> getTableCodeList() {
        return tableCodeList;
    }

    public Query setTableCodeList(List<String> tableCodeList) {
        this.tableCodeList = tableCodeList;
        return this;
    }

    public List<OrderBy> getOrderByList() {
        return orderByList;
    }

    public String getNewTableName() {
        return newTableName;
    }

    public Query setNewTableName(String newTableName) {
        this.newTableName = newTableName;
        return this;
    }

    public String getNewTableCode() {
        return newTableCode;
    }

    public Query setNewTableCode(String newTableCode) {
        this.newTableCode = newTableCode;
        return this;
    }

    public Integer getCreateType() {
        return createType;
    }

    public Query setCreateType(Integer createType) {
        this.createType = createType;
        return this;
    }
}
