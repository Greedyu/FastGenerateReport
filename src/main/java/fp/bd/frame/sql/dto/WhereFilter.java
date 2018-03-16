package fp.bd.frame.sql.dto;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;

import java.util.List;

@APIValueObject("过滤条件")
public class WhereFilter {
    @APIField("是否有组合条件")
    private Boolean multipleFlag = false;

    @APIField("过滤条件列表")
    private List<WhereFilter> whereFilterList;

    @APIField("过滤方式")
    private String filterWay = " = ";

    @APIField("第一个条件")
    private String firstCondition;

    @APIField("第二个条件 between and 这种，有则填写")
    private String secondCondition;

    @APIField("条件间关联关系")
    private String relation = " and";

    @APIField("表中文名称")
    private String tableName;

    @APIField("表")
    private String tableCode;

    @APIField("字段中文名")
    private String fieldName;

    @APIField("字段")
    private String fieldCode;

    @APIField("是否为表达式 0表示不是，则sql需要加单引号")
    private Integer arithmeticFlag = 0;

    public Boolean getMultipleFlag() {
        return multipleFlag;
    }

    public WhereFilter setMultipleFlag(Boolean multipleFlag) {
        this.multipleFlag = multipleFlag;
        return this;
    }

    public List<WhereFilter> getWhereFilterList() {
        return whereFilterList;
    }

    public WhereFilter setWhereFilterList(List<WhereFilter> whereFilterList) {
        this.whereFilterList = whereFilterList;
        return this;
    }

    public String getFilterWay() {
        return filterWay;
    }

    public WhereFilter setFilterWay(String filterWay) {
        this.filterWay = filterWay;
        return this;
    }

    public String getFirstCondition() {
        return firstCondition;
    }

    public WhereFilter setFirstCondition(String firstCondition) {
        this.firstCondition = firstCondition;
        return this;
    }

    public String getSecondCondition() {
        return secondCondition;
    }

    public WhereFilter setSecondCondition(String secondCondition) {
        this.secondCondition = secondCondition;
        return this;
    }

    public String getRelation() {
        return relation;
    }

    public WhereFilter setRelation(String relation) {
        this.relation = relation;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public WhereFilter setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableCode() {
        return tableCode;
    }

    public WhereFilter setTableCode(String tableCode) {
        this.tableCode = tableCode;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public WhereFilter setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public WhereFilter setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
        return this;
    }

    public Integer getArithmeticFlag() {
        return arithmeticFlag;
    }

    public WhereFilter setArithmeticFlag(Integer arithmeticFlag) {
        this.arithmeticFlag = arithmeticFlag;
        return this;
    }
}
